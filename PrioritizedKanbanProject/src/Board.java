import java.util.List;

public class Board {
    private String name;
    private List<Column> columns;

    public Board(String name){
        this.name = name;
    }

    public void setName(String newName){
        if(newName == null || newName.equals(name))
            throw new IllegalArgumentException("a board must have a name.");
    }

    public String getName(){
        return name;
    }

    public void addColumn(String columnName){//}, int index){
        Column col = new Column(columnName);
        if(columns.stream().noneMatch(x -> x.getName().equals(columnName)))
            columns.add(col);
        else throw new IllegalArgumentException("a column must have a distinct name.");
    }

    public boolean removeColumn(String columnName){
        return columns.removeIf(x -> x.getName().equals(columnName));
    }

    public void repositionColumn(String columnName, int index){

    }
}
