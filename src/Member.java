public abstract class Member {
    private char memberType;
    private int memberID;
    private String name;
    private double fees;

    public Member(char pMemberType, int pMemberID, String pName, double pFees) {
        this.memberType = pMemberType;
        this.memberID = pMemberID;
        this.name = pName;
        this.fees = pFees;
    }

    public char getMemberType() {
        return memberType;
    }

    public void setMemberType(char pMemberType) {
        this.memberType = pMemberType;
    }

    public int getMemberID() {
        return memberID;
    }

    public void setMemberID(int pmemberID) {
        this.memberID = pmemberID;
    }

    public String getName() {
        return name;
    }

    public void setName(String pname) {
        this.name = pname;
    }

    public double getFees() {
        return fees;
    }

    public void setFees(double pfees) {
        this.fees = pfees;
    }

    @Override
    public String toString() {
        //return super.toString();
        return "Name: " + this.name + " MemberID: " + this.memberID + " MemberType: " + this.memberType + " Fees: " + this.fees;
    }

    public String toCSV() {
        return this.memberType + ", " + this.memberID + ", " + this.name + ", " + this.fees;
    }
}
