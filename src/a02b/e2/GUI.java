package a02b.e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {
    
    private boolean doRestart = false;
    private final Logic logics;
    private final Map<JButton, Position> cells = new HashMap<>();
    
    public GUI(int size) {
        this.logics = new LogicImpl(size, size);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);

        JPanel main = new JPanel(new BorderLayout());
        JPanel panel = new JPanel(new GridLayout(size,size));
        JButton checkRestart = new JButton("Check > Restart");
        this.getContentPane().add(main);
        main.add(BorderLayout.CENTER, panel);
        main.add(BorderLayout.SOUTH, checkRestart);
        
        ActionListener al = new ActionListener(){
            public void actionPerformed(ActionEvent e){
        	    var button = (JButton)e.getSource();
        	    var position = cells.get(button);
                button.setText(logics.click(position) ? "*" : " ");
            }
        };
        
        checkRestart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (doRestart) {
                    logics.restart();
                    restart();
                } else if (logics.check()) {
                    check();
                    doRestart = true;
                }
            }
        });

        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton(" ");
                this.cells.put(jb, new Position(j, i));
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }

    private void check() {
        for (final var position : logics.getDisabledDiagonal()) {
            cells.entrySet()
                .stream()
                .filter(t -> t.getValue().equals(position))
                .map(t -> t.getKey())
                .forEach(t -> t.setEnabled(false));
        }
    }
    
    private void restart() {
        doRestart = false;
        cells.keySet()
            .stream()
            .forEach(t -> t.setEnabled(true));
        cells.keySet()
            .stream()
            .forEach(t -> t.setText(" "));
    }
}
