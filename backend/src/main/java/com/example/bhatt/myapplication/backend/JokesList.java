package com.example.bhatt.myapplication.backend;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by bhatt on 29-06-2017.
 */



public class JokesList {
    private ArrayList<String> arrayList = new ArrayList<>();

    private Random random;
    private String jock;

    public String getJoke(){

        random = new Random();

        arrayList.add("A snail walks into a bar and the barman tells him there's a strict policy about having snails in the bar and so kicks him out. A year later the same snail re-enters the bar and asks the barman \"What did you do that for?\" ");

        arrayList.add("Mother: \"Did you enjoy your first day at school?\" \n" +
                "Girl: \"First day? Do you mean I have to go back tomorrow? ");

        arrayList.add("A: Hey, man! Please call me a taxi. \n" +
                "B: Yes, sir. You are a taxi. ");

        arrayList.add("A: Why are you crying? \n" +
                "B: The elephant is dead. \n" +
                "A: Was he your pet? \n" +
                "B: No, but I'm the one who must dig his grave. ");

        arrayList.add("A teenage girl had been talking on the phone for about half an hour, and then she hung up.\n" +
                "\"Wow!,\" said her father, \"That was short. You usually talk for two hours. What happened?\"\n" +
                "\n" +
                "\"Wrong number,\" replied the girl.\n" +
                "\n");

        jock = arrayList.get(random.nextInt(arrayList.size()));


        return jock;
    }


}
