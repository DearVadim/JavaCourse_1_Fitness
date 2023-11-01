import java.io.*;
import java.util.LinkedList;

public class FileHandler {

    public LinkedList<Member> readFile(){
        String mem, SubString, name;
        int BeginIndex, EndIndex, memberID, club, membershipPoints;
        char memberType;
        double fees;
        LinkedList<Member> members = new LinkedList<>();
        boolean Err=false;

        System.out.print("Reading datafile...");
        try (BufferedReader FileReader = new BufferedReader(new FileReader("members.csv"))){

            while ((mem = FileReader.readLine()) != null){
                if (mem.length() <= 4 || ! mem.contains(", ")){
                    System.out.println("A line with missing data was found in the members.csv file: '" + mem + "'.");
                    continue;
                }

                // чтение поля memberType
                BeginIndex = 0;
                EndIndex = mem.indexOf(", ", BeginIndex);
                SubString = mem.substring(BeginIndex, EndIndex);
                if(SubString.equals("s") || SubString.equals("m")) {
                    memberType = SubString.charAt(0);
                 }else {
                    Err = true;
                    System.out.println("A string with an unrecognized memberType field '\" + SubString + \"' was found in the members.csv file.");
                    continue;
                }

                // чтение поля memberID
                BeginIndex = EndIndex+2;
                EndIndex = mem.indexOf(", ", BeginIndex);
                SubString = mem.substring(BeginIndex, EndIndex);
                try {
                    memberID = Integer.parseInt(SubString);
                }catch (NumberFormatException ex){
                    Err = true;
                    System.out.println("A string with an unrecognized memberID field: '" + SubString + "' was found in the members.csv file.");
                    continue;
                }

                // чтение поля name
                BeginIndex = EndIndex+2;
                EndIndex = mem.indexOf(", ", BeginIndex);
                name = mem.substring(BeginIndex, EndIndex);

                // чтение поля fees
                BeginIndex = EndIndex+2;
                EndIndex = mem.indexOf(", ", BeginIndex);
                SubString = mem.substring(BeginIndex, EndIndex);
                try {
                    fees = Double.parseDouble(SubString);
                }catch (NumberFormatException ex){
                    Err = true;
                    System.out.println("A string with an unrecognized fees field: '" + SubString + "' was found in the members.csv file.");
                    continue;
                }

                BeginIndex = EndIndex+2;
                SubString = mem.substring(BeginIndex);
                switch (memberType){
                    case 's':
                        // чтение поля club
                        try {
                            club = Integer.parseInt(SubString);

                            members.add(new SingleClubMember(memberType,memberID,name,fees,club));
                        }catch (NumberFormatException ex){
                            Err = true;
                            System.out.println("A string with an unrecognized club field: '" + SubString + "' was found in the members.csv file.");
                            continue;
                        }
                        break;

                    case 'm':
                        // чтение поля membershipPoints
                        try {
                            membershipPoints = Integer.parseInt(SubString);

                            members.add(new MultiClubMember(memberType,memberID,name,fees,membershipPoints));
                        }catch (NumberFormatException ex){
                            Err = true;
                            System.out.println("A string with an unrecognized membershipPoints field: '" + SubString + "' was found in the members.csv file.");
                        }
                }
            }
        }catch (FileNotFoundException e){
            Err = true;
            System.out.println("File not found");
        }catch (IOException ex){
            Err = true;
            System.out.println(ex.getMessage());
        }

        if(!Err){System.out.println("Done");}
        System.out.println();
        return members;
    }

    public boolean appendFile(Member mbr){

        try (BufferedWriter FileWriter = new BufferedWriter(new FileWriter("members.csv", true))){
            FileWriter.write(mbr.toCSV()+"\n");
            FileWriter.flush();
            return true;
        }catch (FileNotFoundException e){
            System.out.println("File not found");
        }catch (IOException ex){
            System.out.println(ex.getMessage());
        }
        return false;
    }

    public boolean overwriteFile(LinkedList<Member> m) {

        try {
            File TempFile = new File("members.temp");
            File MainFile = new File("members.csv");

            if (TempFile.exists()) {
                if(!TempFile.delete()) {
                    System.out.println("File members.temp is already exist and cannot be deleted!");
                    return false;
                }
            }
            if(!TempFile.createNewFile()){
                System.out.println("The members.temp file could not be created!");
                return false;
            }

            FileWriter FW = new FileWriter("members.temp", false);
            for (Member mbr : m) {
                FW.write(mbr.toCSV() + "\n");
            }
            FW.flush();
            FW.close();

            if(MainFile.exists()){
                if(!MainFile.delete()) {
                    System.out.println("File members.csv cannot be deleted!");
                    return false;
                }
            }

            if(!TempFile.renameTo(MainFile)){
                System.out.println("The members.temp file could not be renamed to members.csv!");
                return false;
            }

            } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
