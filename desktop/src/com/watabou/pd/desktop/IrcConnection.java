package com.watabou.pd.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.PircBot;

import java.io.IOException;

public class IrcConnection extends PircBot {

    IrcInputProcessor inputProcessor;

    public IrcConnection(IrcInputProcessor inputProcessor) {

        this.inputProcessor = inputProcessor;

        // Enable debugging output.
        this.setVerbose(true);

        this.setName("uaBArtBot");
        // Connect to the IRC server.
        try {
            this.connect("irc.twitch.tv", 6667, "oauth:9ldmg9z1p0uesld6gpa0cqn3i2btkt");
        } catch (IOException | IrcException e) {
            e.printStackTrace();
        }

        // Join the #uabart channel.
        this.joinChannel("#uabart");
    }

    private void pressKeyButton(int code) {
        final int keycode = code;
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                inputProcessor.keyDown(keycode);
                inputProcessor.keyUp(keycode);
            }
        });
    }

    @Override
    public void onMessage(String channel, String sender,
                          String login, String hostname, String message) {
        GLog.i(sender + ": " + message);
        if (message.equalsIgnoreCase("time")) {
            String time = new java.util.Date().toString();
            send(channel, sender + ": The time is now " + time);
        }

        if (message.equalsIgnoreCase("help")) {
            send(channel, "Commands: help, time, up, down, left, right, pickup, info, catalogus, journal, rest, " +
                    "search, lookaround, backpack, zoomin, zoomout, zoomreset, esc");
        }
        if (message.equalsIgnoreCase("esc")) {
            pressKeyButton(Input.Keys.BACK);
        }
        if (message.equalsIgnoreCase("zoomreset")) {
            pressKeyButton(Input.Keys.SLASH);
        }
        if (message.equalsIgnoreCase("zoomout")) {
            pressKeyButton(Input.Keys.MINUS);
        }
        if (message.equalsIgnoreCase("zoomin")) {
            pressKeyButton(Input.Keys.PLUS);
        }
        if (message.equalsIgnoreCase("backpack")) {
            pressKeyButton(Input.Keys.I);
        }
        if (message.equalsIgnoreCase("lookaround")) {
            pressKeyButton(Input.Keys.S);
            pressKeyButton(Input.Keys.S);
        }
        if (message.equalsIgnoreCase("search")) {
            pressKeyButton(Input.Keys.S);
        }
        if (message.equalsIgnoreCase("rest")) {
            pressKeyButton(Input.Keys.SPACE);
        }
        if (message.equalsIgnoreCase("journal")) {
            pressKeyButton(Input.Keys.J);
        }
        if (message.equalsIgnoreCase("catalogus")) {
            pressKeyButton(Input.Keys.C);
        }
        if (message.equalsIgnoreCase("info")) {
            pressKeyButton(Input.Keys.H);
        }
        if (message.equalsIgnoreCase("pickup")) {
            pressKeyButton(Input.Keys.ENTER);
        }
        if (message.equalsIgnoreCase("left")) {
            pressKeyButton(Input.Keys.LEFT);
        }
        if (message.equalsIgnoreCase("right")) {
            pressKeyButton(Input.Keys.RIGHT);
        }
        if (message.equalsIgnoreCase("up")) {
            pressKeyButton(Input.Keys.UP);
        }
        if (message.equalsIgnoreCase("down")) {
            pressKeyButton(Input.Keys.DOWN);
        }


    }

    public void send(String channel, String message) {
        GLog.p(channel + " > " + message);
        sendMessage(channel, message);
    }

}
