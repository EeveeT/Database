package Database;

import Common.Nothing;
import Common.Value;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Column implements Serializable {

    private final List<Value> elements;

    public Column() {
        this.elements = new ArrayList<>();
    }

    public void addElement(Value element){

        elements.add(element);
    }

    public void removeElement(int elementIndex){

        elements.remove(elementIndex);

    }

    public int getNumElements(){

        return elements.size();
    }


    public void extendColumnBy(int numRows){

        for (int i = 0; i < numRows; i++ ) {

            this.addElement(new Nothing());
        }
    }

    public Value getValue(int index){

        return elements.get(index);

    }

    public void setValue(int index, Value element){

        elements.set(index, element);

    }
}
