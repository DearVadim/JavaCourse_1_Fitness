import java.util.LinkedList;

public class JavaProject {
    public static void main(String[] args) {
        int choice;
        String mem;
        Member mbr;
        MembershipManagement mm = new MembershipManagement();

        FileHandler fh = new FileHandler();
        LinkedList<Member> members = fh.readFile();

        do{
            choice = mm.getChoice();
            System.out.println();
            switch (choice){
                case 1://Add Member
                    mbr = mm.addMembers(members);
                    if(!(mbr ==null)){
                        if(fh.appendFile(mbr)){
                            System.out.println("\nNew member added:");
                            System.out.println(mbr);
                        }
                    }
                    break;
                case 2://Remove Member
                    mem = mm.removeMember(members);
                    if(!(mem ==null)){
                        if(fh.overwriteFile(members)){
                            System.out.println("\nThe member deleted:");
                            System.out.println(mem);
                        }
                    }
                    break;
                case 3://Display Member Information
                    mm.printMemberInfo(members);
                    break;
                case -1://quit
                    break;
                default:
                    System.out.println("Incorrect input. Please try again.");
                    break;
            }
            if(choice == -1){break;}//quit

            System.out.println();

        }while(true);

        System.out.println("Good bye!");
    }
}