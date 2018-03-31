/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MassCharacterRecognition;

import ImageProcessing.AdditionalChar;
import ImageProcessing.ImageTools;
import ImageProcessing.PixelFormation;
import ImageProcessing.SortingOutput;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mathew
 */
public class LineSorter {
    
    private List<PixelFormation> formations;
    public LineSorter(List<PixelFormation> formationsToSort){
        formations = formationsToSort;
    }
    
         private  List<PixelFormation>sortByX(List<PixelFormation> toSort){
        List<PixelFormation> xSorted = new ArrayList<PixelFormation>();
        while(toSort.size()>0){
            int biggest =0;
        for(int i =1; i< toSort.size(); i++){
            if(toSort.get(i).getBounds().getX()< toSort.get(biggest).getBounds().getX()) biggest = i;
        }
        xSorted.add(toSort.get(biggest));
        toSort.remove(biggest);
        }
        return xSorted;
        
    }
     private  List<PixelFormation>sortByY(List<PixelFormation> toSort){
        List<PixelFormation> ySorted = new ArrayList<PixelFormation>();
        while(toSort.size()>0){
            int biggest =0;
        for(int i =1; i< toSort.size(); i++){
            if(toSort.get(i).getBounds().getY()< toSort.get(biggest).getBounds().getY()) biggest = i;
        }
        ySorted.add(toSort.get(biggest));
        toSort.remove(biggest);
        }
        return ySorted;
        
    }
     
     public static SortingOutput sortLines(List<PixelFormation> pf , List<Line> lines){
         List<PixelFormation> newOrderPF = new ArrayList<PixelFormation>();
         List<AdditionalChar> additional = new ArrayList<AdditionalChar>();
        List<Line> newOrderLines = new ArrayList<Line>();
        
         while(lines.size()> 0){
             int smallest =0;
             for(int i=1; i< lines.size(); i++){
                 if(pf.get(lines.get(i).start).getBounds().getY()<pf.get(lines.get(smallest).start).getBounds().getY()) smallest =i;
             }
             newOrderLines.add(lines.get(smallest));
             lines.remove(smallest);
         }
         
         for(Line l : newOrderLines){
             for(int i = l.start; i< l.end+1; i++) newOrderPF.add(pf.get(i));
             additional.add(new AdditionalChar('\n', newOrderPF.size()-1));
            
             
         }
         
         return new SortingOutput(additional, newOrderPF);
        
     }
    
    public  SortingOutput sortLeftToRight(){
        formations = sortByY(formations);
        formations = sortByX(formations);
       
       
        List<PixelFormation> newOrder = new ArrayList<PixelFormation>();
        int l =0;
        int closest =0;
        List<Line> lines = new ArrayList<Line>(); // Keeps track of the starts and end of lines
        while(formations.size()>0){
            
            l++;
          //  closest =  getTopLeft(formations);
            javafx.scene.shape.Rectangle current = formations.get(closest).getBounds();
            newOrder.add(formations.get(closest));
            int start = newOrder.size()-1;
            formations.remove(closest);
            for(int i =0; i<formations.size(); i++){
                
                javafx.scene.shape.Rectangle check = formations.get(i).getBounds();
                
                if((check.getY()>=current.getY() && check.getY()<=(current.getY()+current.getHeight()))
                        ||((current.getY()+current.getHeight())>=check.getY()  && (current.getY()+current.getHeight())<= check.getY() + check.getHeight() || ((current.getY())>check.getY()&&current.getY()<check.getY()+check.getHeight()) ||((current.getY()+current.getHeight())>check.getY()&&current.getY()+current.getHeight()<check.getY()+check.getHeight()) )
                        )
                
               {
                    
                    newOrder.add(formations.get(i));
                    current = formations.get(i).getBounds();
                    formations.remove(i);
                    i--;
                   
                }
                
                
            }
            lines.add(new Line(start, newOrder.size()-1));
            //additional.add( new AdditionalChar('\n',newOrder.size()-2));
        }
        
        
        
        System.out.println(l+" lines detected");
        
        
        return  sortLines(newOrder, lines);
    }
}
