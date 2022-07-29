import java.util.ArrayList;

public class Epic extends Task {
    protected ArrayList<Integer> subTasksIDs;

    public Epic(int id, String name, String description, String status) {
        super(id, name, description, status);
        subTasksIDs = new ArrayList<>();
    }
    public void addSubTask(int subTaskID) {
        this.subTasksIDs.add(subTaskID);
    }

    public ArrayList<Integer> getSubTasksIDs() {
        return subTasksIDs;
    }

    public void setSubTasksIDs(ArrayList<Integer> subTasksIDs) {
        this.subTasksIDs = subTasksIDs;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subTasksIDs=" + subTasksIDs +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
