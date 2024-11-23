package raven.message;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URI;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTargetAdapter;

import javaswingdev.FontAwesome;
import javaswingdev.sm3d.ModelItem;
import javaswingdev.sm3d.SocialMedia3D;
import javaswingdev.sm3d.SocialMediaEvent;
import raven.swing.Glass;

public class SocialMedia extends javax.swing.JDialog {

	private static final long serialVersionUID = 1L;
	private final JFrame fram;
	private Animator animator;
	private Glass glass;
	private boolean show;

	public SocialMedia(JFrame fram) {
		super(fram, true);
		this.fram = fram;
		initComponents();
		init();
	}

	private void init() {
		setBackground(new Color(0, 0, 0, 0));
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		StyledDocument doc = lbTitle.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		lbTitle.setOpaque(false);
		lbTitle.setBackground(new Color(0, 0, 0, 0));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closeMessage();
			}
		});
		animator = new Animator(115, new TimingTargetAdapter() {
			@Override
			public void timingEvent(float fraction) {
				float f = show ? fraction : 1f - fraction;
				glass.setAlpha(f - f * 0.3f);
				setOpacity(f);
			}

			@Override
			public void end() {
				if (!show) {
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

	public void showMessage(String title) {
		fram.setGlassPane(glass);
		glass.setVisible(true);
		lbTitle.setText(title);
		setLocationRelativeTo(fram);
		startAnimator(true);
		setVisible(true);
	}

	public void closeMessage() {
		startAnimator(false);
	}

	public void setLinks(String youtube, String whatsapp, String facebook, String website, String instagram,
			String twitter) {
		socialMedia3D1.addItem(new ModelItem("Youtube", youtube, FontAwesome.YOUTUBE_PLAY, Color.RED,
				new Color(204, 0, 0), new Color(229, 0, 0)));
		socialMedia3D1.addItem(new ModelItem("Website", website, FontAwesome.GLOBE, new Color(70, 156, 235),
				new Color(48, 107, 161), new Color(48, 107, 161)));
		socialMedia3D1.addItem(new ModelItem("WhatsApp", whatsapp, FontAwesome.WHATSAPP, new Color(74, 220, 63),
				new Color(36, 143, 3), new Color(56, 161, 48)));
		socialMedia3D1.addItem(new ModelItem("Facebook", facebook, FontAwesome.FACEBOOK, new Color(23, 120, 242),
				new Color(17, 93, 188), new Color(20, 106, 214)));
		socialMedia3D1.addItem(new ModelItem("Instagram", instagram, FontAwesome.INSTAGRAM, new Color(193, 53, 132),
				new Color(140, 38, 96), new Color(165, 44, 113)));
		socialMedia3D1.addItem(new ModelItem("Twitter", twitter, FontAwesome.TWITTER, new Color(29, 161, 242),
				new Color(22, 125, 188), new Color(25, 142, 214)));
	}

	private void initComponents() {

		background1 = new raven.message.Background();
		cmdOK = new JButton();
		lbIcon = new javax.swing.JLabel();
		lbTitle = new javax.swing.JTextPane();
		socialMedia3D1 = new SocialMedia3D();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setUndecorated(true);

		background1.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

		cmdOK.setText("OK");
		cmdOK.setBackground(new Color(0x091727));
		cmdOK.setForeground(Color.white);
		cmdOK.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
		cmdOK.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				closeMessage();
			}
		});

		lbIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lbIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/raven/message/about.png"))); // NOI18N

		lbTitle.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
		lbTitle.setForeground(new java.awt.Color(0x091727));
		lbTitle.setEditable(false);
		lbTitle.setFocusable(false);
		lbTitle.setText("Message Title");
		socialMedia3D1.addEvent(new SocialMediaEvent() {
			@Override
			public void selected(ModelItem item) {
				try {
					Desktop.getDesktop().browse(new URI(item.getUrl()));
				} catch (Exception e) {
				}
			}
		});

		javax.swing.GroupLayout background1Layout = new javax.swing.GroupLayout(background1);
		background1.setLayout(background1Layout);
		background1Layout.setHorizontalGroup(background1Layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(background1Layout.createSequentialGroup().addComponent(socialMedia3D1,
						javax.swing.GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE))
				.addComponent(cmdOK, GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, 200)
				.addComponent(lbIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE)
				.addComponent(lbTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE));
		background1Layout
				.setVerticalGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
								background1Layout.createSequentialGroup()
										.addComponent(lbIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 74,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(30, 30, 30).addComponent(lbTitle).addGap(10, 10, 10)
										.addComponent(socialMedia3D1, javax.swing.GroupLayout.PREFERRED_SIZE, 120,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(10, 10, 10).addComponent(cmdOK, javax.swing.GroupLayout.PREFERRED_SIZE,
												50, javax.swing.GroupLayout.PREFERRED_SIZE)));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(background1,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(background1,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		cmdOK.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent ke) {
				if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
					closeMessage();
				}
			}
		});
		pack();
	}// </editor-fold>//GEN-END:initComponents

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private raven.message.Background background1;
	private JButton cmdOK;
	private javax.swing.JLabel lbIcon;
	private javax.swing.JTextPane lbTitle;
	private SocialMedia3D socialMedia3D1;
	// End of variables declaration//GEN-END:variables
}
