package Database;

import Common.Value;

import java.util.ArrayList;
import java.util.List;

public class Column{

    private final List<Value> elements;

    public Column() {
        this.elements = new ArrayList<>();
    }

    public void addElement(Value element){

        elements.add(element);
    }

    private void removeElement(Value element){

        //todo: tricky - iterator and validation
    }

    public int getNumElements(){

        return elements.size();
    }


    public void extendColumnBy(int numRows){

        for (int i = 0; i < numRows; i++ ) {

            this.addElement(new Value.Nothing());
        }
    }

    public Value getValue(int index){

        return elements.get(index);

    }
}
