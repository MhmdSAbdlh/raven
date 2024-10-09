package raven.message;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTargetAdapter;

import raven.swing.Glass;

public class OptionDialog extends javax.swing.JDialog {

	private static final long serialVersionUID = 1L;
	private final JFrame fram;
	private Animator animator;
	private Glass glass;
	private boolean show;
	private MessageType messageType = MessageType.CANCEL;

	public OptionDialog(JFrame fram) {
		super(fram, true);
		this.fram = fram;
		initComponents();
		init();
	}

	private void init() {
		setBackground(new Color(0, 0, 0, 0));
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closeMessage();
			}
		});
		animator = new Animator(150, new TimingTargetAdapter() {
			@Override
			public void timingEvent(float fraction) {
				float f = show ? fraction : 1f - fraction;
				glass.setAlpha(f - f * 0.3f);
				setOpacity(f);
			}

			@Override
			public void end() {
				if (show == false) {
					dispose();
					glass.setVisible(false);
				}
			}
		});
		animator.setResolution(0);
		animator.setAcceleration(.5f);
		animator.setDeceleration(.5f);
		setOpacity(0f);
		glass = new Glass();
	}

	private void startAnimator(boolean show) {
		if (animator.isRunning()) {
			float f = animator.getTimingFraction();
			animator.stop();
			animator.setStartFraction(1f - f);
		} else {
			animator.setStartFraction(0f);
		}
		this.show = show;
		animator.start();
	}

	public void showMessage(String title, String tLeft) {
		fram.setGlassPane(glass);
		glass.setVisible(true);
		lbTitle.setText(title);
		tryLeft.setText(tLeft);
		setLocationRelativeTo(fram);
		startAnimator(true);
		txt.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent ke) {
				if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
					messageType = MessageType.CANCEL;
					fram.setState(Frame.ICONIFIED);
				}
				if (ke.getKeyCode() == KeyEvent.VK_ENTER)
					getRootPane().setDefaultButton(cmdOK);
			}
		});
		txt.setText("");
		setVisible(true);
	}

	public String getPasswod() {
		return txt.getText();
	}

	public void closeMessage() {
		startAnimator(false);
	}

	public MessageType getMessageType() {
		return messageType;
	}

	private void initComponents() {
		background1 = new raven.message.Background();
		cmdCancel = new javax.swing.JButton();
		cmdOK = new javax.swing.JButton();
		lbIcon = new javax.swing.JLabel();
		lbTitle = new javax.swing.JLabel();
		tryLeft = new javax.swing.JLabel();
		txt = new javax.swing.JPasswordField();
		showHide = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setUndecorated(true);

		background1.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

		cmdCancel.setBackground(new java.awt.Color(245, 71, 71));
		cmdCancel.setForeground(Color.white);
		cmdCancel.setText("Cancel");
		cmdCancel.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
		cmdCancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cmdCancelActionPerformed(evt);
			}
		});

		cmdOK.setText("OK");
		cmdOK.setBackground(new Color(0x091727));
		cmdOK.setForeground(Color.white);
		cmdOK.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
		cmdOK.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cmdOKActionPerformed(evt);
			}
		});

		lbIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lbIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/raven/message/lock.png"))); // NOI18N

		lbTitle.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
		lbTitle.setForeground(new java.awt.Color(0x09443c));
		lbTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lbTitle.setText("Message Title");

		tryLeft.setFont(new java.awt.Font("sansserif", Font.ITALIC, 14)); // NOI18N
		tryLeft.setForeground(new java.awt.Color(0x282a2b));
		tryLeft.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		tryLeft.setText("TRY LEFT: 5");

		txt.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
		txt.setForeground(new java.awt.Color(76, 76, 76));

		eyeOpen = new ImageIcon(getClass().getResource("/raven/message/eyeopen.png"));
		eyeClosed = new ImageIcon(getClass().getResource("/raven/message/eyeclosed.png"));
		showHide.setContentAreaFilled(false);
		showHide.setBorderPainted(false);
		showHide.setIcon(eyeClosed);
		showHide.addActionListener(e -> {
			if (showStatus == 0) {
				txt.setEchoChar((char) 0);
				showStatus = 1;
				showHide.setIcon(eyeOpen);
			} else {
				txt.setEchoChar('•');
				showStatus = 0;
				showHide.setIcon(eyeClosed);
			}
		});

		javax.swing.GroupLayout background1Layout = new javax.swing.GroupLayout(background1);
		background1.setLayout(background1Layout);
		background1Layout
				.setHorizontalGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(background1Layout.createSequentialGroup()
								.addComponent(cmdCancel, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
								.addGap(3, 3, 3)
								.addComponent(cmdOK, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE))
						.addComponent(lbIcon, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lbTitle, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(tryLeft, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(background1Layout.createSequentialGroup()
								.addComponent(txt, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
								.addComponent(showHide, javax.swing.GroupLayout.DEFAULT_SIZE, 50, 50)));
		background1Layout.setVerticalGroup(background1Layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, background1Layout.createSequentialGroup()
						.addComponent(lbIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 74,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(30, 30, 30).addComponent(lbTitle).addGap(15, 15, 15)
						.addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(txt, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(showHide, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(20, 20, 20).addComponent(tryLeft).addGap(18, 18, 18)
						.addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(cmdCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(cmdOK, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
										javax.swing.GroupLayout.PREFERRED_SIZE))));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());

		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(background1,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(background1,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void cmdCancelActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cmdCancelActionPerformed
		messageType = MessageType.CANCEL;
		closeMessage();
	}// GEN-LAST:event_cmdCancelActionPerformed

	private void cmdOKActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cmdOKActionPerformed
		messageType = MessageType.OK;
		closeMessage();
	}// GEN-LAST:event_cmdOKActionPerformed

	public void openApp(String pass) {
		txt.setText(pass);
		messageType = MessageType.OK;
		closeMessage();
	}

	public static enum MessageType {
		CANCEL, OK
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private raven.message.Background background1;
	private javax.swing.JButton cmdCancel;
	private javax.swing.JButton cmdOK;
	private javax.swing.JLabel lbIcon;
	private javax.swing.JLabel lbTitle;
	private javax.swing.JPasswordField txt;
	private javax.swing.JButton showHide;
	private javax.swing.JLabel tryLeft;
	private javax.swing.ImageIcon eyeOpen;
	private javax.swing.ImageIcon eyeClosed;
	private int showStatus = 0;
	// End of variables declaration//GEN-END:variables
}
