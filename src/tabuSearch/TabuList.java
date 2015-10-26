package tabuSearch;

public class TabuList {
    private int genSolutionId;
    private int moveA;
    private int moveB;

    public TabuList() {
    }

    public int getMoveA() {
        return moveA;
    }

    public void setMoveA(int moveA) {
        this.moveA = moveA;
    }

    public int getMoveB() {
        return moveB;
    }

    public void setMoveB(int moveB) {
        this.moveB = moveB;
    }

    public int getGenSolutionId() {
        return genSolutionId;
    }

    public void setGenSolutionId(int genSolutionId) {
        this.genSolutionId = genSolutionId;
    }


}
