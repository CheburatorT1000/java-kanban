public class SubTask extends Task {
    protected int parentEpicTaskID;

    public SubTask(int id, String name, String description, String status, int parentEpicTaskID) {
        super(id, name, description, status);
        this.parentEpicTaskID = parentEpicTaskID;
    }

    public int getParentEpicTaskID() {
        return parentEpicTaskID;
    }

    public void setParentEpicTaskID(int parentEpicTaskID) {
        this.parentEpicTaskID = parentEpicTaskID;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "parentEpicTaskID=" + parentEpicTaskID +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
