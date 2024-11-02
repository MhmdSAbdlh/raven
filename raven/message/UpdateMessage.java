package raven.message;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTargetAdapter;

import raven.swing.Glass;

public class UpdateMessage extends javax.swing.JDialog {

	private static final long serialVersionUID = 1L;
	private final JFrame fram;
	private Animator animator;
	private Glass glass;
	private boolean show;
	private MessageType messageType = MessageType.CANCEL;

	public UpdateMessage(JFrame fram) {
		super(fram, true);
		this.fram = fram;
		initComponents();
		init();
	}

	private void init() {
		setBackground(new Color(0, 0, 0, 0));
		StyledDocument doc = txt.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_LEFT);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		txt.setOpaque(false);
		txt.setBackground(new Color(0, 0, 0, 0));
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closeMessage();
			}
		});
		animator = new Animator(100, new TimingTargetAdapter() {
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

	public void showMessage(String title, String message) {
		fram.setGlassPane(glass);
		glass.setVisible(true);
		lbTitle.setText(title);
		txt.setText(message);
		setLocationRelativeTo(fram);
		startAnimator(true);
		setVisible(true);
	}

	public void closeMessage() {
		startAnimator(false);
	}

	public MessageType getMessageType() {
		return messageType;
	}

	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		background1 = new raven.message.Background();
		cmdCancel = new JButton();
		cmdOK = new JButton();
		lbIcon = new javax.swing.JLabel();
		lbTitle = new javax.swing.JLabel();
		txt = new javax.swing.JTextPane();
		scroller = new javax.swing.JScrollPane(txt);

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setUndecorated(true);

		background1.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

		cmdCancel.setBackground(new java.awt.Color(245, 71, 71));
		cmdCancel.setText("Cancel");
		cmdCancel.setForeground(Color.white);
		cmdCancel.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
		cmdCancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cmdCancelActionPerformed(evt);
			}
		});

		cmdOK.setBackground(new java.awt.Color(0x091727));
		cmdOK.setForeground(Color.white);
		cmdOK.setText("OK");
		cmdOK.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
		cmdOK.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cmdOKActionPerformed(evt);
			}
		});

		lbIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lbIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/raven/message/update.png"))); // NOI18N

		lbTitle.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
		lbTitle.setForeground(new java.awt.Color(0x091727));
		lbTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lbTitle.setText("Message Title");

		txt.setEditable(false);
		txt.setFont(new java.awt.Font("sansserif", Font.ITALIC, 16)); // NOI18N
		txt.setForeground(new java.awt.Color(30, 30, 30));
		txt.setText("Message Text\nSimple");
		txt.setFocusable(false);

		scroller.setBorder(null);

		javax.swing.GroupLayout background1Layout = new javax.swing.GroupLayout(background1);
		background1.setLayout(background1Layout);
		background1Layout
				.setHorizontalGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(background1Layout.createSequentialGroup().addGap(20, 20, 20)
								.addComponent(cmdCancel, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
								.addGap(50, 50, 50)
								.addComponent(cmdOK, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
								.addGap(20, 20, 20))
						.addComponent(lbIcon, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lbTitle, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(scroller));
		background1Layout.setVerticalGroup(background1Layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, background1Layout.createSequentialGroup()
						.addComponent(lbIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 74,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(20, 20, 20).addComponent(lbTitle)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(scroller, javax.swing.GroupLayout.PREFERRED_SIZE, 160,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(30, 30, 30)
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
		cmdCancel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent ke) {
				if (ke.getKeyCode() == KeyEvent.VK_ESCAPE)
					closeMessage();
			}
		});
		cmdOK.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent ke) {
				if (ke.getKeyCode() == KeyEvent.VK_ESCAPE)
					closeMessage();
			}
		});
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

	public static enum MessageType {
		CANCEL, OK
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private raven.message.Background background1;
	private JButton cmdCancel;
	private JButton cmdOK;
	private javax.swing.JLabel lbIcon;
	private javax.swing.JLabel lbTitle;
	private javax.swing.JTextPane txt;
	private javax.swing.JScrollPane scroller;
	// End of variables declaration//GEN-END:variables
}
