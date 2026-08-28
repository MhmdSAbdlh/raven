package raven.message;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dialog;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URI;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.formdev.flatlaf.FlatClientProperties;

import javaswingdev.FontAwesome;
import javaswingdev.sm3d.ModelItem;
import javaswingdev.sm3d.OverlayP;
import javaswingdev.sm3d.SocialMedia3D;
import javaswingdev.sm3d.SocialMediaEvent;
import raven.swing.Glass;

public class SocialMediaD extends javax.swing.JDialog {

	private static final long serialVersionUID = 1L;
	private final javax.swing.JDialog fram;
	private Glass glass;
	private OverlayP overlay = new OverlayP();

	public enum APP {
		WHATSAPP, YOUTUBE, TWITTER, WEBSITE, FACEBOOK, INSTAGRAM, PAYPAL
	}

	public SocialMediaD(javax.swing.JDialog fram) {
		super(fram, false);
		this.fram = fram;
		initComponents();
		init();
	}

	private void init() {
		overlay.setBounds(0, 0, fram.getWidth(), fram.getHeight());
		overlay.setOpaque(false);
		overlay.setOverlayColor(fram.getBackground());
		overlay.setAlpha(0);
		Timer fadeInTimer = new Timer(20, null);
		fadeInTimer.addActionListener(_ -> {
			float currentAlpha = overlay.getAlpha();
			if (currentAlpha < 0.5f) {
				overlay.setAlpha(currentAlpha + 0.1f); // Increase opacity gradually
			} else {
				overlay.setAlpha(0.5f);
				fadeInTimer.stop(); // Stop timer when fully visible
			}
		});
		fadeInTimer.start(); // Start fade-in effect
		fram.getLayeredPane().add(overlay, JLayeredPane.PALETTE_LAYER);

		setBackground(new Color(0, 0, 0, 0));
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		StyledDocument doc = lbTitle.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		lbTitle.setOpaque(false);
		lbTitle.setBackground(fram.getForeground());
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closeMessage();
			}
		});
		glass = new Glass();
		overlay.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Point mousePoint = e.getLocationOnScreen();
				Rectangle dialogBounds = getBounds();
				dialogBounds.setLocation(getLocationOnScreen());
				if (!dialogBounds.contains(mousePoint)) {
					closeMessage();
				}
			}
		});
		setModalityType(Dialog.ModalityType.MODELESS);
	}

	public void showMessage(String title) {
		fram.setGlassPane(glass);
		glass.setVisible(true);
		lbTitle.setText(title);
		setLocationRelativeTo(fram);
		setVisible(true);
	}

	public void closeMessage() {
		Timer fadeOutTimer = new Timer(20, null);
		fadeOutTimer.addActionListener(_ -> {
			float currentAlpha = overlay.getAlpha();
			if (currentAlpha > 0.1f) {
				overlay.setAlpha(currentAlpha - 0.1f); // Increase opacity gradually
			} else {
				overlay.setAlpha(0f);
				fram.getLayeredPane().remove(overlay);
				fram.getLayeredPane().repaint();
				fadeOutTimer.stop(); // Stop timer when fully transparent
				dispose();
			}
		});
		fadeOutTimer.start(); // Start fade-out effect
	}

	public void addLinks(APP app, String url) {
		switch (app) {
		case YOUTUBE:
			socialMedia3D1.addItem(new ModelItem("Youtube", url, FontAwesome.YOUTUBE_PLAY, Color.RED,
					new Color(204, 0, 0), new Color(229, 0, 0)));
			break;
		case WEBSITE:
			socialMedia3D1.addItem(new ModelItem("Website", url, FontAwesome.GLOBE, new Color(70, 156, 235),
					new Color(48, 107, 161), new Color(48, 107, 161)));
			break;
		case WHATSAPP:
			socialMedia3D1.addItem(new ModelItem("WhatsApp", url, FontAwesome.WHATSAPP, new Color(74, 220, 63),
					new Color(36, 143, 3), new Color(56, 161, 48)));
			break;
		case FACEBOOK:
			socialMedia3D1.addItem(new ModelItem("Facebook", url, FontAwesome.FACEBOOK, new Color(23, 120, 242),
					new Color(17, 93, 188), new Color(20, 106, 214)));
			break;
		case INSTAGRAM:
			socialMedia3D1.addItem(new ModelItem("Instagram", url, FontAwesome.INSTAGRAM, new Color(193, 53, 132),
					new Color(140, 38, 96), new Color(165, 44, 113)));
			break;
		case TWITTER:
			socialMedia3D1.addItem(new ModelItem("Twitter", url, FontAwesome.TWITTER, new Color(29, 161, 242),
					new Color(22, 125, 188), new Color(25, 142, 214)));
			break;
		case PAYPAL:
			socialMedia3D1.addItem(new ModelItem("Paypal", url, FontAwesome.PAYPAL, new Color(0x121212),
					new Color(0x545454), new Color(0xc4c4c4)));
			break;
		default:
			break;
		}
	}

	public void setTxtColor(Color color) {
		lbTitle.setForeground(color);
	}

	public void setBGColor(Color color) {
		background1.setBackground(color);
		overlay.setOverlayColor(getBackground());
	}

	public void setIcon(ImageIcon icon) {
		lbIcon.setIcon(icon); // NOI18N
	}

	private void initComponents() {
		background1 = new raven.message.Background();
		cmdOK = new JButton();
		lbIcon = new javax.swing.JLabel(icon);
		lbTitle = new javax.swing.JTextPane();
		socialMedia3D1 = new SocialMedia3D();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setUndecorated(true);

		background1.setBackground(fram.getBackground());
		background1.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));

		cmdOK.setText("OK");
		cmdOK.putClientProperty(FlatClientProperties.STYLE,
				"" + "arc:999;" + "margin:3,33,3,33;" + "borderWidth:1;" + "focusWidth:0;" + "innerFocusWidth:0.5;"
						+ "background:null;" + "[light]borderColor:" + "#3B82F6" + ";" + "[dark]borderColor:"
						+ "#3B82F6" + ";" + "[light]focusedBorderColor:" + "#3B82F6" + ";" + "[dark]focusedBorderColor:"
						+ "#3B82F6" + ";" + "[light]focusColor:" + "#3B82F6" + ";" + "[dark]focusColor:" + "#3B82F6"
						+ ";" + "[light]hoverBorderColor:" + "#3B82F6" + ";" + "[dark]hoverBorderColor:" + "#3B82F6"
						+ ";" + "[light]foreground:" + "#3B82F6" + ";" + "[dark]foreground:" + "#3B82F6" + ";");
		cmdOK.setFont(new java.awt.Font("sansserif", java.awt.Font.BOLD, 18));
		cmdOK.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				closeMessage();
			}
		});

		lbIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

		lbTitle.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
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
						javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE))
				.addComponent(cmdOK, GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, 100)
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
										.addGap(30, 30, 30).addComponent(lbTitle).addGap(15, 15, 15)
										.addComponent(socialMedia3D1, javax.swing.GroupLayout.PREFERRED_SIZE, 120,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(15, 15, 15).addComponent(cmdOK, javax.swing.GroupLayout.PREFERRED_SIZE,
												40, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(15, 15, 15)));

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
				if (ke.getKeyCode() == KeyEvent.VK_ESCAPE)
					closeMessage();
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
	private ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource("/raven/message/about.png"));
	// End of variables declaration//GEN-END:variables
}
