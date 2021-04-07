package HTTPserver;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

class ORMClient extends Thread {
    static int ASSIGNMENT_NUM = 1;
    Socket s = new Socket(InetAddress.getByName("localhost"), 8080);
    PrintWriter pw = new PrintWriter(s.getOutputStream());

    String response;
    BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));

    int number;
    ORMClient() throws IOException {
        number = ASSIGNMENT_NUM++;

    }


    @Override
    public void run() {
        //request first line formation.
        try {
            pw.print("POST /");
            pw.print("\\src\\HTTPserver\\index.html");

            pw.print(" HTTP/1.1\r\n");
            //request headers formation.
            pw.print("QUESTION: " + number);
            pw.print("Host: localhost\r\n\r\n");
            //request body formation.

            if (number < 6) {
                pw.print(currentThread().getName() + " with ");
                pw.print("QUESTION" + number + "\r\n");
                //You can use here to print the sending message.
//                System.out.println(currentThread().getName() +"  "+ number);

                // Insert a new book
                if (number == 1) {
                    pw.println("b1"); // Book ID
                    pw.println("Bookname"); // Title
                    pw.println("Herman"); // Publisher
                    pw.println("12/12/12"); // Publication date
                }

                // Update username and password
                if (number == 2) {
                    pw.println("Josefsen1"); // Old username
                    pw.println("Josefsen123"); // Old password
                    pw.println("Johannes"); // New username
                    pw.println("Johannes123"); // New password
                }

                // Delete loan record
                if (number == 3) {
                    pw.println("bor12345"); // Borrower ID
                    pw.println("b12345"); // Book ID
                }
            }

            pw.flush();

            while ((response = br.readLine()) != null) System.out.println(response);


        }catch(Exception e){
            e.printStackTrace();
        }finally{
            pw.close();
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}

class ORMTestClient {

    public static void main(String[] args) throws IOException, InterruptedException {


        ORMClient ormClient1 = new ORMClient();
        ORMClient ormClient2 = new ORMClient();
        ORMClient ormClient3 = new ORMClient();
        ORMClient ormClient4 = new ORMClient();
        ORMClient ormClient5 = new ORMClient();

        ormClient1.setName("Client 1 - ");
        ormClient2.setName("Client 2 - ");
        ormClient3.setName("Client 3 - ");
        ormClient4.setName("Client 4 - ");
        ormClient5.setName("Client 5 - ");

        ormClient1.start();
        ormClient1.sleep(1500);
        ormClient2.start();
        ormClient2.sleep(1500);
        ormClient3.start();
        ormClient3.sleep(1500);
        ormClient4.start();
        ormClient4.sleep(1500);
        ormClient5.start();

    }
}
