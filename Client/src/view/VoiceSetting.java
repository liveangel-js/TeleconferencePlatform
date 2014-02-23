/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * VoiceSetting.java
 *
 * Created on 2012-6-1, 14:06:45
 */
package view;

import java.awt.event.KeyEvent;
import logic.VoiceClient;

/**
 *
 * @author Gyx
 */
public class VoiceSetting extends javax.swing.JFrame {
    private VoiceClient vc;

    /** Creates new form VoiceSetting */
    public VoiceSetting() {
        initComponents();
    }
    
    public VoiceSetting(VoiceClient vc){
        this.vc=vc;
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();

        setTitle("Voice Setting");
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
        });

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setFont(new java.awt.Font("΢���ź�", 1, 14)); // NOI18N
        jRadioButton1.setSelected(true);
        jRadioButton1.setText("Free talk");
        jRadioButton1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jRadioButton1StateChanged(evt);
            }
        });
        jRadioButton1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jRadioButton1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jRadioButton1KeyReleased(evt);
            }
        });

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setFont(jRadioButton1.getFont());
        jRadioButton2.setText("Button to speak  ");
        jRadioButton2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jRadioButton2StateChanged(evt);
            }
        });
        jRadioButton2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jRadioButton2KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jRadioButton2KeyReleased(evt);
            }
        });

        jButton1.setFont(jRadioButton1.getFont());
        jButton1.setText("Finish");
        jButton1.setNextFocusableComponent(this);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jButton1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jButton1KeyReleased(evt);
            }
        });

        jTextField1.setBackground(new java.awt.Color(255, 255, 255));
        jTextField1.setEditable(false);
        jTextField1.setFont(jRadioButton1.getFont());
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setText("F2");
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jRadioButton1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jRadioButton2)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(131, 131, 131)
                        .addComponent(jButton1)))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jRadioButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton2)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
// TODO add your handling code here:
    this.requestFocus();
}//GEN-LAST:event_formMouseReleased

private void jRadioButton1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jRadioButton1StateChanged
// TODO add your handling code here:
     if(jRadioButton1.isSelected()){
        vc.setCaptureAccess(true);
    }
}//GEN-LAST:event_jRadioButton1StateChanged

private void jRadioButton2StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jRadioButton2StateChanged
// TODO add your handling code here:
    if(jRadioButton2.isSelected()){
        vc.setCaptureAccess(false);
    }
}//GEN-LAST:event_jRadioButton2StateChanged

private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
// TODO add your handling code here:
    int code=evt.getKeyCode();
    System.out.println(code);
    if((code>=65 && code <=93)||(code>=44 && code<=57))
        jTextField1.setText(evt.getKeyChar()+"");
    else{
        switch(code){
            case 192:
                jTextField1.setText("~");
                break;
            case 45:
                jTextField1.setText("-");
                break;
            case 61:
                jTextField1.setText("+");
                break;
            case 20:
                jTextField1.setText("CapsLock");
                break;
            case 59:
                jTextField1.setText(";");
                break;
            case 222:
                jTextField1.setText("'");
                break;
            case 16:
                jTextField1.setText("Shift");
                break;
            case 17:
                jTextField1.setText("Ctrl");
                break;
            case 18:
                jTextField1.setText("Alt");
                break;
            case 32:
                jTextField1.setText("Space");
                break;
            case 37:
                jTextField1.setText("Left");
                break;
            case 39:
                jTextField1.setText("Right");
                break;
            case 38:
                jTextField1.setText("Up");
                break;
            case 40:
                jTextField1.setText("Down");
                break;
            case 36:
                jTextField1.setText("Home");
                break;
            case 35:
                jTextField1.setText("End");
                break;
            case 33:
                jTextField1.setText("Page Up");
                break;
            case 34:
                jTextField1.setText("Page Down");
                break;
            case 112:
                jTextField1.setText("F1");
                break;
            case 113:
                jTextField1.setText("F2");
                break;
            case 114:
                jTextField1.setText("F3");
                break;
            case 115:
                jTextField1.setText("F4");
                break;
            case 116:
                jTextField1.setText("F5");
                break;
            case 117:
                jTextField1.setText("F6");
                break;
            case 118:
                jTextField1.setText("F7");
                break;
            case 119:
                jTextField1.setText("F8");
                break;
            case 120:
                jTextField1.setText("F9");
                break;
            case 121:
                jTextField1.setText("F10");
                break;     
            case 122:
                jTextField1.setText("F11");
                break;    
            case 123:
                jTextField1.setText("F12");
                break;
        }
    }
    vc.keycode=code;
    this.requestFocus();
}//GEN-LAST:event_jTextField1KeyPressed

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
// TODO add your handling code here:
    this.dispose();
}//GEN-LAST:event_jButton1ActionPerformed

private void jRadioButton1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jRadioButton1KeyPressed
// TODO add your handling code here:
    checkPress(evt);
}//GEN-LAST:event_jRadioButton1KeyPressed

private void jRadioButton2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jRadioButton2KeyPressed
// TODO add your handling code here:
    checkPress(evt);
}//GEN-LAST:event_jRadioButton2KeyPressed

private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
// TODO add your handling code here:
    checkPress(evt);
}//GEN-LAST:event_formKeyPressed

private void jButton1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton1KeyPressed
// TODO add your handling code here:
    checkPress(evt);
}//GEN-LAST:event_jButton1KeyPressed

private void jButton1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton1KeyReleased
// TODO add your handling code here:
    checkRelease(evt);
}//GEN-LAST:event_jButton1KeyReleased

private void jRadioButton2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jRadioButton2KeyReleased
// TODO add your handling code here:
    checkRelease(evt);
}//GEN-LAST:event_jRadioButton2KeyReleased

private void jRadioButton1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jRadioButton1KeyReleased
// TODO add your handling code here:
    checkRelease(evt);
}//GEN-LAST:event_jRadioButton1KeyReleased

private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
// TODO add your handling code here:
    checkRelease(evt);
}//GEN-LAST:event_formKeyReleased

private void checkPress(java.awt.event.KeyEvent evt){
    if(evt.getKeyCode()==vc.keycode)
        vc.setCaptureAccess(true);
}

private void checkRelease(java.awt.event.KeyEvent evt){
    if(evt.getKeyCode()==vc.keycode)
        vc.setCaptureAccess(false);
}


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
