/* 
* Nome: <Simão Pedro Ribeiro dos Santos> 
* Número: <8200322> 
* Turma: <LEI1T4> 
 */
package Demos;

import edu.ma02.dashboards.Dashboard;

import java.io.IOException;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author simao
 */
public class Demo3 {

    /**
     * Testing the bar chart visualization(brute force)
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        String[] string = new String[2];

        string[0] = "{\n"
                + "   \"type\":\"bar\",\n"
                + "   \"data\":{\n"
                + "      \"labels\":[\n"
                + "         \"Q1\",\n"
                + "         \"Q2\",\n"
                + "         \"Q3\",\n"
                + "         \"Q4\"\n"
                + "      ],\n"
                + "      \"datasets\":[\n"
                + "         {\n"
                + "            \"label\":\"Users\",\n"
                + "            \"data\":[\n"
                + "               50,\n"
                + "               60,\n"
                + "               70,\n"
                + "               5,\n"
                + "            ]\n"
                + "         }\n"
                + "      ]\n"
                + "   }\n"
                + "}";

        string[1] = "{\n"
                + " \"type\":\"bar\",\n"
                + " \"data\":{\n"
                + " \"labels\":[\n"
                + " \"Q1\",\n"
                + " \"Q2\",\n"
                + " \"Q3\",\n"
                + " \"Q4\"\n"
                + " ],\n"
                + " \"datasets\":[\n"
                + " {\n"
                + " \"label\":\"Users\",\n"
                + " \"data\":[\n"
                + " 50,\n"
                + " 60,\n"
                + " 70,\n"
                + " 180\n"
                + " ]\n"
                + " },\n"
                + " {\n"
                + " \"label\":\"Revenue\",\n"
                + " \"data\":[\n"
                + " 100,\n"
                + " 200,\n"
                + " 300,\n"
                + " 400\n"
                + " ]\n"
                + " }\n"
                + " ]\n"
                + " }\n"
                + "}";

        try {
            Dashboard.render(string);
        } catch (IOException ex) {
            Logger.getLogger(Demo3.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
