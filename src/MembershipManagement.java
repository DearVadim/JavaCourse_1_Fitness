import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

public class MembershipManagement {
    final private Scanner reader = new Scanner(System.in);
    private int getIntInput() {
        int input;

        do {
            try {
                input = reader.nextInt();
                reader.nextLine();
                break;
            } catch (InputMismatchException exc) {
                reader.nextLine();
                System.out.print("Incorrect input. Please try again: ");
            }
        } while (true);
        return input;
    }

    private void printClubOptions() {
        System.out.println("1) Club Mercury");
        System.out.println("2) Club Neptune");
        System.out.println("3) Club Jupiter");
        System.out.println("4) Multi Clubs");
        System.out.print("Please select a club (or enter -1 to quit): ");
    }

    public int getChoice() {
        int choice;
        do {
            System.out.println("WELCOME TO OZONE FITNESS CENTER");
            System.out.println("================================");
            System.out.println("1) Add Member");
            System.out.println("2) Remove Member");
            System.out.println("3) Display Member Information");
            System.out.print("Please select an option (or enter -1 to quit): ");
            choice = getIntInput();
            if (choice == -1 || (choice >= 1 && choice <= 3)) {
                break;
            } else {
                System.out.println("Incorrect input. Please try again.");
            }
        } while (true);
        return choice;
    }


    public Member addMembers(LinkedList<Member> m) {
        String name;
        int club, memberID, maxID;
        double fees;
        Member mbr;
        Calculator cal;
        final int MembershipPoints = 100;

        cal = (int x) -> switch (x) {
            case 1 -> 900;
            case 2 -> 950;
            case 3 -> 1000;
            case 4 -> 1200;
            default -> -1;
        };

        //ввод номера клуба
        do {
            printClubOptions();
            club = getIntInput();
            if (club >= 1 && club <= 4) {
                break;
            } else if (club==-1) {
                break;
            } else {
                System.out.println("Incorrect input. Please try again");
            }
        } while (true);

        if (club==-1){return null;}

         //определение цены
        fees = cal.calculateFees(club);

        //ввод имени
        do{
            System.out.print("Please enter a member's name (or enter -1 to quit): ");
            name = reader.nextLine();
            if(name.equals("")){
                System.out.println("The name can't be empty!");
            } else if (name.equals("-1")) {
                break;
            }else{
                break;
            }
        }while(true);

        if (name.equals("-1")){return null;}

        //определение memberID
        maxID = m.size()+1;
        memberID = 1;
        for(int i=1; i<=maxID; i++){
            int finalI = i;
            if(m.stream().anyMatch(x->x.getMemberID()== finalI)){
                continue;
            }
            memberID = i;
            break;
        }

        //создание объекта
        if(club==4){
            mbr = new MultiClubMember('m', memberID, name, fees, MembershipPoints);
        }else{
            mbr = new SingleClubMember('s', memberID, name, fees, club);
        }

        m.add(mbr);

        return mbr;
    }

    public String removeMember(LinkedList<Member> m) {
        int MemberID;
        long MembersCount;
        String DeletedMemberStr=null;
        Member DeletedMemberObj=null;


        //если список клиентов пустой, сразу возврат
        if (m.size() == 0) {
            System.out.println("List of members is empty.");
            return null;
        }

        do {
            System.out.print("Please enter a member's ID to delete (or -1 to quit): ");
            MemberID = getIntInput();
            if (MemberID == -1) {
                break;
            }

            int finalMemberID = MemberID;
            MembersCount = m.stream().filter(member -> member.getMemberID() == finalMemberID).count();
            if (MembersCount==0) {
                System.out.printf("A member with ID = %d not found.\n", MemberID);

            } else if(MembersCount==1){
                for (Member mbr:m) {
                    if(mbr.getMemberID()==MemberID){
                        DeletedMemberStr = mbr + "\n";
                        DeletedMemberObj = mbr;
                        break;
                    }
                }
                m.remove(DeletedMemberObj);

                break;
            }else System.out.println("More than 1 member with this ID!");
        } while (true);

        return DeletedMemberStr;
    }

    public void printMemberInfo(LinkedList<Member> m) {
        int MemberID;
        boolean AnyMatch;


        //если список клиентов пустой, сразу возврат
        if (m.size() == 0) {
            System.out.println("List of members is empty.");
            return;
        }

        do {
            System.out.print("Please enter a member's ID to display the information (or -1 to quit): ");
            MemberID = getIntInput();
            if (MemberID == -1) {
                return;
            }

            int finalMemberID = MemberID;
            AnyMatch = m.stream().anyMatch(member -> member.getMemberID() == finalMemberID);
            if (!AnyMatch) {
                System.out.printf("A member with ID = %d not found.\n", MemberID);

            } else {
                m.stream().filter(member -> member.getMemberID() == finalMemberID).forEach(System.out::println);
                System.out.println();
            }
        } while (true);
    }
}