public class SingleClubMember extends Member{
    private int club;

    public SingleClubMember(char pMemberType, int pMemberID, String pName, double pFees, int pClub) {
        super(pMemberType, pMemberID, pName, pFees);
        this.club = pClub;
    }

    public int getClub() {
        return club;
    }

    public void setClub(int club) {
        this.club = club;
    }

    @Override
    public String toString() {
        return super.toString() + " Club: " + this.club;
    }

    @Override
    public String toCSV() {
        return super.toCSV() + ", " + this.club;
    }
}
