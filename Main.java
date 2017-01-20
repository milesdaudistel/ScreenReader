import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Hello, enjoy your new Robobot.  Robobot is very handy.  It can help your find things. \n" +
                "Please enter a command.  If you don't know any commands, try out help. \n");
        String input = "";
        while (!input.equals("standby")) {
            System.out.print("bot: ");
            input = sc.nextLine().toLowerCase();

            switch (input) {
                case "camera":
                    //have robobot take a picture
                    //SelectBot robo = new SelectBot();
                    SelectBot.main(null);
                    //robobot.camera();
                    break;
                case "search":
                    //have the robot search the screen for the picture
                    //print error if there is no picture to look for
                    //robobot.search();
                    SearchBot.main(null);
                    break;
                case "help":
                    //print out all the possible commands
                    System.out.println(_help);
                    break;
                case "specify":
                    //prompt user for location and size of subscreenshot to look for
                    System.out.print("Please enter an x , y, width and height for your your subshot: ");
                    //robobot.specify(sc.nextLine());
                    break;
                case "standby":
                    System.out.println("Standing by...");
                    System.exit(0);
                    break;
                case "manualsetscreen":
                    //set your screen width and height manually if robobot doesn't find it
                    break;
                case "screen size":
                    //print out the dimensions of the screen
                    //robobot.printScreenDimensions();
                    break;
                case "show screenshot":
                    //pull up the screenshot in whatever application
                    System.out.println("Not implemented");
                    break;
                case "00":
                    SearchBot.coords0();
                    break;
                default:
                    System.out.println("Unrecognized command.");
                    break;
            }

        }
    }

    /** Help message. */
    private static final String _help = "Here is a list of commands you can give Robobot: \n" +
            "---------------------------\n" +
            "Camera:  Take a screenshot \n" +
            "Search: if possible, locates your current selection on the screen \n" +
            "Help: try the help command for further details \n" +
            "Specify: tell Robobot what part of the image you want it to look for in the future \n " +
            "Standby: Power down Robobot \n" +
            "Screen size \n" +
            "---------------------------\n";

}
