package Client.Pages.Components;

import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import Client.Pages.OnlineExamUI;

public class TimerUI extends JPanel implements Runnable {
    JLabel time;
    int ti;
    OnlineExamUI ui;
    public TimerUI(OnlineExamUI ui, Dimension bounds, int x1, int y1, int x2, int y2, int timei) {
        Util.setSize(this, bounds, x1, y1, x2, y2);
        this.ui = ui;
        setLayout(new BorderLayout());
        time = new JLabel("", JLabel.CENTER);
        time.setForeground(Util.timerColor1);
        time.setFont(Util.examTitleFont);
        setTime(timei);
        ti = timei;

        add(time);
        
    }
    private void setTime(int i) {
        time.setText(i/60 + "mins " + i%60 + "sec");
        revalidate();
    }
    public void start() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(this, 0, 1, TimeUnit.SECONDS);
    }

    @Override
    public void run() {
        ti = ti -1;
        if (ti < 5*60 && time.getForeground() != Util.timerColor2) {
            time.setForeground(Util.timerColor2);
        }
        if (ti  <= 0) {
            ui.endExamTimer();
            return;
        }
        setTime(ti);
    }
}