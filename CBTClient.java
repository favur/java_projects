/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cbtsystemclientversion;
//
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import static java.lang.Thread.sleep;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
//
public class CBTSystemClientVersion extends JFrame {
    static CBTSystemClientVersion resWizard = null;
    //
    static ImageIcon infoIcon = new ImageIcon("images/windows-info.png"),
            errorIcon = new ImageIcon("images/windows-error.png"),
            warningIcon = new ImageIcon("images/windows-warning.png"),
            questionIcon = new ImageIcon("images/windows-question.png");
    //
    Date date = new Date();
    String minute, second, hour, day, mon, year; //for control of dates display
    String timeDisposition = "",
            recieptCopy = "",
            aniText = "",
            realHour = "", realMinute = "", timeDispose = "";
    //
    JLabel dateTimeLab = new JLabel(),
            nigeriaLab = new JLabel(""),
            designLab = new JLabel("",
            JLabel.LEFT);
    static JLabel promptLab = new JLabel("");
    //
    static String licenceYear = "",
            databaseName = "mecuryservercbt",
            titleText = "CBT Systems",
            businessOption = "",
            getLoginUserName = "",
            dateCollector = "",
            getSchoolName = "",
            getSchoolAddress = "",
            getSchoolLogo = "",
            schoolSection = "",
            nextSchoolTerm = "",
            schoolHolidayClass = "",
            companyPrincipalSign = "",
            classRoomTeacherSign = "";
    //
    boolean startScrollFlag = true;
    //
    JPanel topHolder = new JPanel(),
            southPanel = new JPanel(),
            centerHolder = new JPanel();
    //
    JPasswordField loginPwdField = new JPasswordField();
    JTextField copyRightLab = new JTextField(50);
    //
    JButton loginButton = new JButton(new ImageIcon("images/next.jpg"));
    //
    Color themeColor = Color.CYAN;
    //font option
    static String getFontType = "canbria";
    //
    JLabel instrLab = new JLabel(" *press enter"),
            loginDepartmentNameLab = null;
    //
    JPanel loginFieldPanel = new JPanel(),
            loginButtonPanel = new JPanel(),
            loginDepartmentNameLabPanel = new JPanel(),
            loginComponentsPanel = new JPanel(),
            loginComponentsLeftPanel = new JPanel();
    //
    String[] userList = {"UserName:   ", "Password:   "};
    JLabel[] userLab = new JLabel[userList.length];
    JComboBox userNameBox = null;
    JPasswordField userPasswordField = new JPasswordField();
    JButton userLoginButton = new JButton(" LOGIN ");
    JLabel  changePasswordLab = new JLabel("change password"),
            newUserLab = new JLabel("new account"),
            logoutLab = null;
    //
    String[] uList = null;
    String getUserName = "";
    //    
    String getServerIP = "";
    public CBTSystemClientVersion() {
        //
        add(centerHolder, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
        validate();
        //
        mon = convertToNumericMonth(date.toString().substring(4, 7));
        day = date.toString().substring(8, 10); //current day
        year = date.toString().substring(26, 28); //2 digit year value
        licenceYear = year;
        //
        minute = date.toString().substring(14, 16);
        second = date.toString().substring(17, 19);
        timeDisposition = setAmPm();
        //
        dateCollector = (day + "-" + mon + "-" + year + " "
                + "" + hour + ":" + minute + ":" + second + " "
                + "" + timeDisposition);
        //threads control
        timeDisplay timer = new timeDisplay(hour, minute, second, date);
        timer.start();
        //
        new callHomePage();
    }
    //
    class callHomePage {

        JPanel blinkingPanel = new JPanel(),
                blinkingBoxPanel = new JPanel();
        JRadioButton[] blinkingBox = new JRadioButton[4];
        boolean blinkControl = true;
        //
        public callHomePage() {
            //
            getSchoolName = getOneDetail("select schoolname from schoolprofile");
            getSchoolAddress = getOneDetail("select schooladdress from schoolprofile");
            //
            getSchoolName = (getSchoolName.equalsIgnoreCase("nil") ? 
                        "" : getSchoolName).toUpperCase();
            
            loginDepartmentNameLab = new JLabel(getSchoolName);
            //
            int uC = getNumberOfDetails("select * from user");
            uList = populateList("select username from user", uC);
            for(int pos = 0; pos < uC; pos++) {
                uList[pos] = uList[pos].substring(0, 1).toUpperCase() + "" + 
                        uList[pos].substring(1).toLowerCase();
            }
            //
            userNameBox = new JComboBox(uList);
            //
            userNameBox.setFont(new Font(getFontType, Font.PLAIN, 14));
            userNameBox.setForeground(Color.BLACK);
            instrLab.setFont(new Font(getFontType, Font.BOLD, 10));
            instrLab.setForeground(Color.RED);
            nigeriaLab.setFont(new Font(getFontType, Font.BOLD, 9));
            nigeriaLab.setForeground(Color.RED);
            dateTimeLab.setForeground(Color.BLUE);
            dateTimeLab.setFont(new Font(getFontType, Font.BOLD, 18));
            loginDepartmentNameLab.setForeground(Color.ORANGE);
            loginDepartmentNameLab.setFont(new Font(getFontType, Font.PLAIN, 20));
            changePasswordLab.setForeground(Color.BLUE);
            changePasswordLab.setFont(new Font(getFontType, Font.BOLD, 10));
            changePasswordLab.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            newUserLab.setForeground(Color.BLUE);
            newUserLab.setFont(new Font(getFontType, Font.BOLD, 10));
            newUserLab.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            userPasswordField.setFont(new Font(getFontType, Font.PLAIN, 14));
            userPasswordField.setForeground(Color.BLACK);
            userPasswordField.setBorder(BorderFactory.createEtchedBorder());
            userLoginButton.setFont(new Font(getFontType, Font.BOLD, 10));
            userLoginButton.setForeground(Color.RED);
            userLoginButton.setBackground(Color.WHITE);
            //layout of the north panel
            topHolder.setBorder(BorderFactory.createEtchedBorder());
            topHolder.setBackground(Color.WHITE);
            topHolder.add(new JLabel(new ImageIcon("images/baseline.png")));
            //
            for (int pos = 0; pos < userList.length; pos++) {
                userLab[pos] = new JLabel(userList[pos], JLabel.RIGHT);
                userLab[pos].setForeground(Color.BLACK);
                userLab[pos].setFont(new Font(getFontType, Font.PLAIN, 14));
            }
            //
            loginFieldPanel.setLayout(new GridLayout(userList.length, 2, -10, 5));
            loginFieldPanel.add(userLab[0]);
            loginFieldPanel.add(userNameBox);
            loginFieldPanel.add(userLab[1]);
            loginFieldPanel.add(userPasswordField);
            //
            loginButtonPanel.add(new JLabel("                          "));
            loginButtonPanel.add(changePasswordLab);
            loginButtonPanel.add(new JLabel(" | "));                                
            loginButtonPanel.add(newUserLab);                             
            loginButtonPanel.add(new JLabel("                          "));
            loginButtonPanel.add(userLoginButton);
            //
            loginDepartmentNameLabPanel.add(loginDepartmentNameLab);
            //
            loginComponentsPanel.setBorder(BorderFactory.createEtchedBorder());
            loginComponentsPanel.setLayout(new BorderLayout());
            loginComponentsPanel.add(loginDepartmentNameLabPanel, BorderLayout.NORTH);
            loginComponentsPanel.add(loginFieldPanel, BorderLayout.CENTER);
            loginComponentsPanel.add(loginButtonPanel, BorderLayout.SOUTH);
            //
            loginComponentsLeftPanel.setLayout(new BorderLayout());
            loginComponentsLeftPanel.add(loginComponentsPanel, BorderLayout.WEST);
            //
            loginComponentsLeftPanel.setBackground(themeColor);
            loginComponentsPanel.setBackground(themeColor);
            loginFieldPanel.setBackground(themeColor);
            loginButtonPanel.setBackground(themeColor);
            //
            centerHolder.setBackground(themeColor);
            centerHolder.setLayout(new BorderLayout());
            centerHolder.add(loginComponentsLeftPanel, BorderLayout.NORTH);
            centerHolder.add(new JLabel(new ImageIcon("images/logo1.png"),
                    JLabel.RIGHT), BorderLayout.SOUTH);
            //
            blinkingBoxPanel.setBackground(themeColor);
            blinkingPanel.setBackground(themeColor);
            blinkingBoxPanel.setLayout(
                    new GridLayout(1, blinkingBox.length, 10, 0));
            for (int x = 0; x < blinkingBox.length; x++) {
                blinkingBox[x] = new JRadioButton();
                blinkingBox[x].setBackground(themeColor);
                blinkingBox[x].setBorder(BorderFactory.createEtchedBorder());
                blinkingBoxPanel.add(blinkingBox[x]);
            }
            //
            aniText = " (c)Powered By OrionSoftech For "
                    + getSchoolName.toUpperCase() + " ";
            copyRightLab.setForeground(Color.RED);
            copyRightLab.setBackground(themeColor);
            copyRightLab.setFont(new Font(getFontType, Font.BOLD, 14));
            copyRightLab.setEditable(false);
            copyRightLab.setBorder(BorderFactory.createEmptyBorder());
            copyRightLab.setHorizontalAlignment(SwingConstants.RIGHT);
            //
            JPanel copyRightPanel = new JPanel();
            copyRightPanel.setBackground(themeColor);
            copyRightPanel.add(copyRightLab);
            //
            blinkingPanel.setLayout(new BorderLayout());
            blinkingPanel.add(blinkingBoxPanel, BorderLayout.EAST);
            blinkingPanel.add(copyRightPanel, BorderLayout.WEST);
            //
            southPanel.setBackground(themeColor);
            southPanel.setLayout(new BorderLayout());
            southPanel.add(blinkingPanel, BorderLayout.SOUTH);
            //
            add(topHolder, BorderLayout.NORTH);
            add(centerHolder, BorderLayout.CENTER);
            add(southPanel, BorderLayout.SOUTH);
            validate();
            //
            newUserLab.addMouseListener(new newUserControl());
            changePasswordLab.addMouseListener(new changePasswordControl());
            userLoginButton.addActionListener(new loginControl());
            userPasswordField.addActionListener(new alternativeLogin());
            //
            new databaseVerifier().start();
            new blinkingBoxControl().start();
            new scrollSchoolNameControl().start();
            //
            userNameBox.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    for(int pos = 0; pos < uList.length; pos++) {
                        if(pos == userNameBox.getSelectedIndex()) {
                            getUserName = uList[pos].toLowerCase();
                        }
                    }
                }
            });
        }
        //
        createNewUser createUser = null;
        //
        class newUserControl extends MouseAdapter {
            
            public void mouseClicked(MouseEvent e) {
                createUser = new createNewUser();
                createUser.setSize(400, 260);
                createUser.setTitle(titleText + " - Create New Account");
                createUser.setResizable(false);
                Dimension frame = createUser.getToolkit().getScreenSize();
                int height = createUser.getHeight(),
                        width = createUser.getWidth();
                createUser.setLocation((frame.width - width) * 3 / 6,
                        (frame.height - height) / 2);
                createUser.setVisible(true);
                //
                createUser.setIconImage(
                        Toolkit.getDefaultToolkit().getImage("reflection.gif"));
            }
        }
        //
        class createNewUser extends JDialog {

            String[] passList = {" PASSWORD: ", " CONFIRM PASSWORD: "};
            JLabel[] passwordLab = new JLabel[passList.length];
            JPasswordField[] pwdField = new JPasswordField[passList.length];
            JTextField oldField = new JTextField("type name in full", 20);
            JButton passButton = new JButton(" CREATE "),
                    closeButton = new JButton(" CLOSE ");
            //
            JPanel oldPassFieldPanel = new JPanel();
            JLabel oldPasswordLab = new JLabel(" USER NAME: ", JLabel.RIGHT);
            JPanel centerPanel = new JPanel(),
                    southFieldPanel = new JPanel(),
                    buttonPanel = new JPanel();
            //
            JLabel oldInstrcLab = new JLabel(" TYPE USER NAME--------------", JLabel.LEFT),
                    newInstrcLab = new JLabel(" TYPE PASSWORD--------------", JLabel.LEFT),
                    oldInstrcSpaceLab = new JLabel(""),
                    newInstrcSpaceLab = new JLabel("");
            //
            JLabel categoryLab = new JLabel(" ASSIGN AS: ", JLabel.RIGHT);
            String[] userCategory = {"ADMIN STAFF", "LECTURER"};
            JComboBox userBox = new JComboBox(userCategory);
            String getCategory = "";
            //
            public createNewUser() {
                southFieldPanel.setLayout(new GridLayout(7, 2, 0, 2));
                passButton.setFont(new Font(getFontType, Font.BOLD, 10));
                passButton.setBackground(Color.WHITE);
                passButton.setForeground(Color.GREEN);
                closeButton.setFont(new Font(getFontType, Font.BOLD, 10));
                closeButton.setBackground(Color.WHITE);
                closeButton.setForeground(Color.RED);
                oldInstrcLab.setFont(new Font(getFontType, Font.PLAIN, 12));
                oldPasswordLab.setFont(new Font(getFontType, Font.PLAIN, 12));
                newInstrcLab.setFont(new Font(getFontType, Font.PLAIN, 12));
                newInstrcSpaceLab.setFont(new Font(getFontType, Font.PLAIN, 12));
                newInstrcSpaceLab.setFont(new Font(getFontType, Font.PLAIN, 12));
                categoryLab.setFont(new Font(getFontType, Font.PLAIN, 12));
                userBox.setBackground(Color.WHITE);
                userBox.setBackground(Color.WHITE);
                userBox.setFont(new Font(getFontType, Font.PLAIN, 12));
                oldInstrcLab.setForeground(Color.RED);
                newInstrcLab.setForeground(Color.RED);
                newInstrcSpaceLab.setForeground(Color.RED);
                oldInstrcSpaceLab.setForeground(Color.RED);
                //
                southFieldPanel.add(oldInstrcLab);
                southFieldPanel.add(oldInstrcSpaceLab);
                southFieldPanel.add(oldPasswordLab);
                southFieldPanel.add(oldField);
                southFieldPanel.add(categoryLab);
                southFieldPanel.add(userBox);
                southFieldPanel.add(newInstrcLab);
                southFieldPanel.add(newInstrcSpaceLab);
                //
                for (int x = 0; x < passList.length; x++) {
                    passwordLab[x] = new JLabel(passList[x], JLabel.RIGHT);
                    passwordLab[x].setFont(new Font(getFontType, Font.PLAIN, 12));
                    pwdField[x] = new JPasswordField(20);
                    pwdField[x].setBorder(BorderFactory.createEtchedBorder());
                    southFieldPanel.add(passwordLab[x]);
                    southFieldPanel.add(pwdField[x]);
                }
                //
                pwdField[0].setToolTipText("A minimum of 8 characters is allowed");
                //
                oldField.setBorder(BorderFactory.createEtchedBorder());
                buttonPanel.setLayout(new FlowLayout());
                buttonPanel.add(closeButton);
                buttonPanel.add(new JLabel("                                "));
                buttonPanel.add(passButton);
                buttonPanel.setBorder(BorderFactory.createEtchedBorder());
                buttonPanel.setBackground(themeColor);
                //
                add(southFieldPanel, BorderLayout.NORTH);
                add(buttonPanel, BorderLayout.SOUTH);
                validate();
                //
                userBox.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        for (int x = 0; x < userCategory.length; x++) {
                            if (x == userBox.getSelectedIndex()) {
                                getCategory = userCategory[x].toLowerCase();
                            }
                        }
                    }
                });
                //
                closeButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        createUser.setVisible(false);
                        validate();
                    }
                });
                //
                passButton.addActionListener(new setPasswordDetails()); //confirm pass entry
            }
            //
            class setPasswordDetails implements ActionListener {
                //
                public void actionPerformed(ActionEvent e) {
                    String rep = checkPassEntries();
                    if (rep.equalsIgnoreCase("true")) {
                        insertUpdateDelete("insert into user values ("
                                + "'" + new String(pwdField[0].getPassword()).toLowerCase() + "', "
                                + "'" + oldField.getText().toLowerCase() + "', "
                                + "'" + getCategory.toLowerCase() + "')");
                        //
                        promptLab.setText("User Successfully Created. "
                                + "You Can Now Login With Your New Credentials.");
                        JOptionPane.showMessageDialog(null,
                                new JScrollPane(promptLab),
                                getSchoolName.toUpperCase(),
                                JOptionPane.INFORMATION_MESSAGE,
                                infoIcon);
                        createUser.setVisible(false);
                        //
                        int uC = getNumberOfDetails("select * from user");
                        //
                        if (uC > 0) {
                            uList = populateList("select username from user", uC);
                            //
                            userNameBox.removeAllItems();
                            for (int pos = 0; pos < uC; pos++) {
                                uList[pos] = uList[pos].substring(0, 1).toUpperCase() + "" + 
                                    uList[pos].substring(1).toLowerCase();
                                //
                                userNameBox.addItem(
                                        uList[pos].toString().toUpperCase());
                            }
                        }
                        //
                        getUserName = "";
                    } 
                    else {
                        promptLab.setText("User Not Created. "
                                + "Parameter Provided Not Valid.");
                        JOptionPane.showMessageDialog(null,
                                new JScrollPane(promptLab),
                                getSchoolName.toUpperCase(),
                                JOptionPane.ERROR_MESSAGE,
                                errorIcon);
                    }
                    validate();
                }
                //check all entries for validity

                String checkPassEntries() {
                    String validate = "true";
                    if (oldField.getText().equalsIgnoreCase("")
                            && new String(pwdField[0].getPassword()).equalsIgnoreCase("")
                            && new String(pwdField[1].getPassword()).equalsIgnoreCase("")) {
                        promptLab.setText("Entry Field(s) Cannot Be Empty. "
                                + "Please Enter The Required Information.");
                        JOptionPane.showMessageDialog(null,
                                new JScrollPane(promptLab),
                                getSchoolName.toUpperCase(),
                                JOptionPane.INFORMATION_MESSAGE,
                                infoIcon);
                        validate = "false";
                    } else if (oldField.getText().equalsIgnoreCase(
                            new String(pwdField[0].getPassword()))) {
                        promptLab.setText("Your user Name And Password Cannot Be the Same.");
                        JOptionPane.showMessageDialog(null,
                                new JScrollPane(promptLab),
                                getSchoolName.toUpperCase(),
                                JOptionPane.ERROR_MESSAGE,
                                errorIcon);
                        validate = "false";
                    } else if (!new String(pwdField[0].getPassword()).equalsIgnoreCase(""
                            + "" + new String(pwdField[1].getPassword()))) {
                        promptLab.setText("New Password And Confirm Password "
                                + "Provided Don't Match.");
                        JOptionPane.showMessageDialog(null,
                                new JScrollPane(promptLab),
                                getSchoolName.toUpperCase(),
                                JOptionPane.ERROR_MESSAGE,
                                errorIcon);
                        validate = "false";
                    } else if (new String(pwdField[0].getPassword()).length() < 6) {
                        promptLab.setText("Minimum Of Six (6) Characters For Password Text Violated. "
                                + "A Minimun Of Six Characters Is Required.");
                        JOptionPane.showMessageDialog(null,
                                new JScrollPane(promptLab),
                                getSchoolName.toUpperCase(),
                                JOptionPane.WARNING_MESSAGE,
                                warningIcon);
                        validate = "false";
                    } else if (new String(pwdField[0].getPassword()).length() > 15) {
                        promptLab.setText("Maximum Of Fifteen Characters For Password Text Violated. "
                                + "A Maximun Of Fifteen (15) Characters Is Required.");
                        JOptionPane.showMessageDialog(null,
                                new JScrollPane(promptLab),
                                getSchoolName.toUpperCase(),
                                JOptionPane.WARNING_MESSAGE,
                                warningIcon);
                        validate = "false";
                    } else if (new String(pwdField[0].getPassword()).equalsIgnoreCase(""
                            + "" + getOneDetail("select password from user "
                            + "where password = '" + new String(pwdField[0].getPassword()) + "'"))) {
                        promptLab.setText("Sorry. Password Suggested Cannot Be Used. "
                                + "Please Specify Another Password.");
                        JOptionPane.showMessageDialog(null,
                                new JScrollPane(promptLab),
                                getSchoolName.toUpperCase(),
                                JOptionPane.WARNING_MESSAGE,
                                warningIcon);
                        validate = "false";
                    }
                    else if (oldField.getText().equalsIgnoreCase(""
                            + "" + getOneDetail("select username from user "
                            + "where username = '" + oldField.getText().toLowerCase() + "'"))) {
                        promptLab.setText("Sorry. User Name Already Collected. "
                                + "Please Specify Another Password.");
                        JOptionPane.showMessageDialog(null,
                                new JScrollPane(promptLab),
                                getSchoolName.toUpperCase(),
                                JOptionPane.WARNING_MESSAGE,
                                warningIcon);
                        validate = "false";
                    }
                    return validate;
                }
            }
        }
        //
        class scrollSchoolNameControl extends Thread {
            int pos = 0;
            String txt,
                    space = "   ", //ri  ght padding space
                    newtxt;
            //
            public void run() {
                txt = aniText;
                newtxt = "";
                while (startScrollFlag) {
                    scrollTextHandler();
                }
            }
            //
            void scrollTextHandler() {
                try {
                    newtxt += "" + txt.charAt(pos); //start text scroll from right
                    copyRightLab.setText(newtxt);
                    validate();
                    //
                    try {
                        sleep(100); //1/4 of a second
                    } catch (InterruptedException e) {
                    }
                    pos++;
                    //
                    if (pos == txt.length() - 1) {
                        while (pos > 0) {
                            newtxt += space; //remove scroll from left by adding space
                            newtxt = newtxt.substring(1); //and removing one character at a time
                            copyRightLab.setText(newtxt);
                            validate();
                            //
                            try {
                                sleep(100);
                            } catch (InterruptedException e) {
                            }
                            pos--;
                            //
                            if (pos == 0) { //restart scroll when complete
                                copyRightLab.setText("");
                                newtxt = "";
                                try {
                                    sleep(3000); //1/4 of a second
                                } catch (InterruptedException e) {
                                }
                                break;
                            }
                        }
                    }
                } catch (StringIndexOutOfBoundsException b) {
                }
            }
        }
        //
        class databaseVerifier extends Thread {
            
            public void run() {
                while(true) {    
                    try {
                        sleep(60000);
                    } catch (InterruptedException e) {
                    }
                    //
                    databaseName = 
                        getOneDetail("select dataname from schoolprofile");
                    //
                    if(databaseName.equalsIgnoreCase("")) {
                        //
                        promptLab.setText("Please Load Server Application And "
                                + "Reboot This Client Application");
                        promptLab.setForeground(Color.RED);
                        promptLab.setFont(new Font(getFontType, Font.BOLD, 20));
                        promptLab.setHorizontalAlignment(JLabel.CENTER);
                        //
                        centerHolder.removeAll();
                        centerHolder.setLayout(new BorderLayout());
                        centerHolder.setBackground(Color.WHITE);
                        centerHolder.add(promptLab, BorderLayout.NORTH);
                        centerHolder.add(new JLabel(
                                new ImageIcon("images/reload.gif")), 
                                BorderLayout.CENTER);
                        //
                        add(centerHolder, BorderLayout.CENTER);
                        validate();
                    }
                }    
            }
        }
        //
        class blinkingBoxControl extends Thread {

            int counter = 0;

            public void run() {
                while (blinkControl) {
                    blinkingBox[counter].setSelected(true);
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                    }
                    //
                    for (int x = 0; x < blinkingBox.length; x++) {
                        blinkingBox[x].setSelected(false);
                    }
                    counter++;
                    if (counter == blinkingBox.length - 1) {
                        blinkingBox[counter].setSelected(true);
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                        }
                        counter = 0;
                        for (int x = 0; x < blinkingBox.length; x++) {
                            blinkingBox[x].setSelected(false);
                        }
                    }
                }
            }
        }
        //
        class changePasswordControl extends MouseAdapter {

            changeMyAdminPassword changePassword = null;
            //
            public void mouseClicked(MouseEvent e) {
                //
                if(getUserName.equalsIgnoreCase("student")) {
                    promptLab.setText("Sorry, Students Are Not Allowed To "
                            + "Change Default Password");
                    JOptionPane.showMessageDialog(null, 
                            promptLab,
                            getSchoolName.toUpperCase(),
                            JOptionPane.WARNING_MESSAGE,
                            warningIcon);
                }
                else {
                    changePassword = new changeMyAdminPassword();
                    changePassword.setSize(400, 200);
                    changePassword.setTitle(getUserName.toUpperCase() + " - PASSWORD CHANGE");
                    changePassword.setResizable(false);
                    Dimension frame = changePassword.getToolkit().getScreenSize();
                    int height = changePassword.getHeight(),
                            width = changePassword.getWidth();
                    changePassword.setLocation(
                            (frame.width - width) * 3 / 6,
                            (frame.height - height) / 2);
                    changePassword.setVisible(true);
                    changePassword.setIconImage(
                            Toolkit.getDefaultToolkit().getImage("reflection.gif"));
                    changePassword.setDefaultCloseOperation(changePassword.HIDE_ON_CLOSE);
                }
            }
            //
            class changeMyAdminPassword extends JDialog {

                String[] passList = {" NEW PASSWORD: ", " CONFIRM PASSWORD: "};
                JLabel[] passwordLab = new JLabel[passList.length];
                JPasswordField[] pwdField = new JPasswordField[passList.length];
                JTextField oldField = new JTextField(20);
                JButton passButton = new JButton(" CHANGE "),
                        clearButton = new JButton(" CLEAR "),
                        closeButton = new JButton(" CLOSE ");
                //
                JPanel oldPassFieldPanel = new JPanel();
                JLabel oldPasswordLab = new JLabel(" OLD PASSWORD: ", JLabel.RIGHT);
                JPanel centerPanel = new JPanel(),
                        southFieldPanel = new JPanel(),
                        buttonPanel = new JPanel();
                //
                JLabel oldInstrcLab = new JLabel(" TYPE OLD PASSWORD--------------", JLabel.LEFT),
                        newInstrcLab = new JLabel(" TYPE NEW PASSWORD--------------", JLabel.LEFT),
                        oldInstrcSpaceLab = new JLabel(""),
                        newInstrcSpaceLab = new JLabel("");
                //
                String prevComment = "";

                public changeMyAdminPassword() {
                    prevComment = promptLab.getText();
                    //
                    southFieldPanel.setLayout(new GridLayout(6, 2, 0, 2));
                    passButton.setFont(new Font(getFontType, Font.BOLD, 10));
                    passButton.setBackground(Color.WHITE);
                    passButton.setForeground(Color.GREEN);
                    clearButton.setFont(new Font(getFontType, Font.BOLD, 10));
                    clearButton.setBackground(Color.WHITE);
                    clearButton.setForeground(Color.BLUE);
                    closeButton.setFont(new Font(getFontType, Font.BOLD, 10));
                    closeButton.setBackground(Color.WHITE);
                    closeButton.setForeground(Color.RED);
                    oldInstrcLab.setFont(new Font(getFontType, Font.PLAIN, 12));
                    oldPasswordLab.setFont(new Font(getFontType, Font.PLAIN, 12));
                    newInstrcLab.setFont(new Font(getFontType, Font.PLAIN, 12));
                    newInstrcSpaceLab.setFont(new Font(getFontType, Font.PLAIN, 12));
                    newInstrcSpaceLab.setFont(new Font(getFontType, Font.PLAIN, 12));
                    oldInstrcLab.setForeground(Color.RED);
                    newInstrcLab.setForeground(Color.RED);
                    newInstrcSpaceLab.setForeground(Color.RED);
                    oldInstrcSpaceLab.setForeground(Color.RED);
                    //
                    southFieldPanel.add(oldInstrcLab);
                    southFieldPanel.add(oldInstrcSpaceLab);
                    southFieldPanel.add(oldPasswordLab);
                    southFieldPanel.add(oldField);
                    southFieldPanel.add(new JLabel(""));
                    southFieldPanel.add(new JLabel(""));
                    southFieldPanel.add(newInstrcLab);
                    southFieldPanel.add(newInstrcSpaceLab);
                    //
                    for (int x = 0; x < passList.length; x++) {
                        passwordLab[x] = new JLabel(passList[x], JLabel.RIGHT);
                        passwordLab[x].setFont(new Font(getFontType, Font.PLAIN, 12));
                        pwdField[x] = new JPasswordField(20);
                        pwdField[x].setBorder(BorderFactory.createEtchedBorder());
                        southFieldPanel.add(passwordLab[x]);
                        southFieldPanel.add(pwdField[x]);
                    }
                    //
                    pwdField[0].setToolTipText("A minimum of 8 characters is allowed");
                    //
                    oldField.setBorder(BorderFactory.createEtchedBorder());
                    buttonPanel.setLayout(new FlowLayout());
                    buttonPanel.add(closeButton);
                    buttonPanel.add(new JLabel("                                "));
                    buttonPanel.add(passButton);
                    buttonPanel.add(new JLabel("      "));
                    buttonPanel.add(clearButton);
                    //
                    add(southFieldPanel, BorderLayout.NORTH);
                    add(buttonPanel, BorderLayout.SOUTH);
                    validate();
                    //
                    closeButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            promptLab.setText(prevComment);
                            changePassword.setVisible(false);
                            validate();
                        }
                    });
                    //
                    clearButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            pwdField[0].setText("");
                            pwdField[1].setText("");
                            oldField.setText("");
                            validate();
                        }
                    });
                    passButton.addActionListener(new setPasswordDetails()); //confirm pass entry
                }
                //

                class setPasswordDetails implements ActionListener {
                    //

                    public void actionPerformed(ActionEvent e) {
                        String rep = checkPassEntries();
                        if (rep.equalsIgnoreCase("true")) {
                            insertUpdateDelete("update user "
                                    + "set password = '" + new String(pwdField[0].getPassword()) + "' "
                                    + "where password = '" + oldField.getText() + "'");
                            promptLab.setText(" User Accounts' Password Changed.");
                            JOptionPane.showMessageDialog(null,
                                    promptLab,
                                    getSchoolName.toUpperCase(),
                                    JOptionPane.INFORMATION_MESSAGE,
                                    infoIcon);
                            changePassword.setVisible(false);
                        }
                        validate();
                    }
                    //check all entries for validity

                    String checkPassEntries() {
                        String validate = "true";
                        if (oldField.getText().equalsIgnoreCase("")
                                && new String(pwdField[0].getPassword()).equalsIgnoreCase("")
                                && new String(pwdField[1].getPassword()).equalsIgnoreCase("")) {
                            promptLab.setText(" Entry Field(s) Cannot Be Empty. "
                                    + "Please Enter The Required Information.");
                            validate = "false";
                        } else if (!oldField.getText().equalsIgnoreCase(
                                getOneDetail("select password from user "
                                + "where password = '" + oldField.getText() + "'"))) {
                            promptLab.setText(" Old Password Enter Not Recognised.");
                            validate = "false";
                        } else if (new String(pwdField[0].getPassword()).equalsIgnoreCase(
                                getOneDetail("select password from user "
                                + "where password = '" + new String(pwdField[0].getPassword()) + "'"))) {
                            promptLab.setText(" Password Is Reserved Already.");
                            validate = "false";
                        } else if (oldField.getText().equalsIgnoreCase(
                                new String(pwdField[0].getPassword()))) {
                            promptLab.setText(" Your Old And New Password Cannot Be the Same.");
                            validate = "false";
                        } else if (!new String(pwdField[0].getPassword()).equalsIgnoreCase(""
                                + "" + new String(pwdField[1].getPassword()))) {
                            promptLab.setText(" New Password And Confirm Password "
                                    + "Don't Match.");
                            validate = "false";
                        } else if (new String(pwdField[0].getPassword()).length() < 4) {
                            promptLab.setText(" A Minimun Of Four (4) Characters Is Required.");
                            validate = "false";
                        } else if (new String(pwdField[0].getPassword()).length() > 15) {
                            promptLab.setText(" A Maximun Of Fifteen Characters Password Is Required.");
                            validate = "false";
                        }
                        //
                        JOptionPane.showMessageDialog(null,
                                promptLab,
                                getSchoolName.toUpperCase(),
                                JOptionPane.INFORMATION_MESSAGE,
                                infoIcon);
                        return validate;
                    }
                }
            }
        }
        //
        class alternativeLogin implements ActionListener {

            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == userPasswordField) {
                    if (getUserName.equalsIgnoreCase("")) {
                        promptLab.setText("Entry Field Cannot Be Empty. "
                                + "Please Enter Your Login Information.");
                        JOptionPane.showMessageDialog(null,
                                promptLab,
                                getSchoolName.toUpperCase(),
                                JOptionPane.INFORMATION_MESSAGE,
                                infoIcon);
                    } else if (!new String(userPasswordField.getPassword()).equalsIgnoreCase(""
                            + "" + getOneDetail("select password from user "
                            + "where username = '" + getUserName + "'"))) {
                        promptLab.setText("Wrong Password, Please Try Again.");
                        JOptionPane.showMessageDialog(null,
                                promptLab,
                                getSchoolName.toUpperCase(),
                                JOptionPane.WARNING_MESSAGE,
                                warningIcon);
                        userPasswordField.setText("");
                    } 
                    else {
                        //
                        new administrativeHomePage();
                        //
                        userPasswordField.setText("");
                        resWizard.setTitle(" " + titleText + " " + getSchoolName);
                    }
                }
                validate();
            }
        }
        //
        class loginControl implements ActionListener {

            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == userLoginButton) {
                    if (getUserName.equalsIgnoreCase("")) {
                        promptLab.setText("Entry Field Cannot Be Empty. "
                                + "Please Enter The Required Information.");
                        JOptionPane.showMessageDialog(null,
                                promptLab,
                                getSchoolName.toUpperCase(),
                                JOptionPane.INFORMATION_MESSAGE,
                                infoIcon);
                    } 
                    else if (!new String(userPasswordField.getPassword()).equalsIgnoreCase(""
                            + "" + getOneDetail("select password from user "
                            + "where username = '" + getUserName + "'"))) {
                        promptLab.setText("Wrong Password, Please Try Again.");
                        JOptionPane.showMessageDialog(null,
                                promptLab,
                                getSchoolName.toUpperCase(),
                                JOptionPane.WARNING_MESSAGE,
                                warningIcon);
                        userPasswordField.setText("");
                    } 
                    else {
                        //
                        new administrativeHomePage();
                        //
                        userPasswordField.setText("");
                        resWizard.setTitle(" " + titleText + " " + getSchoolName);
                    }
                }
                validate();
            }
        }
    }
    //
    class administrativeHomePage {
        String[] getCategory = new String[4];
        JTabbedPane tabbedHolder = new JTabbedPane();
        //
        JLabel commTestLab = new JLabel(" Ready!");
        //
        String printExcelText = "";
        //
        serverConfigPage serverPage = null;
        //
        public administrativeHomePage() {
            //
            getLoginUserName = getUserName;
            //
            centerHolder.removeAll();
            tabbedHolder.setBackground(themeColor);
            tabbedHolder.setTabPlacement(JTabbedPane.LEFT);
            tabbedHolder.setFont(new Font(getFontType, Font.PLAIN, 12));
            //
            String getCategoryName = 
                    getOneDetail("select userstatus from user "
                    + "where username = '" + getLoginUserName.toLowerCase() + "'");
            //
            if(getLoginUserName.equalsIgnoreCase("admin")) {
                serverPage = new serverConfigPage();
                serverPage.setSize(200, 400);
                serverPage.setTitle("Config Server");
                Dimension frame = serverPage.getToolkit().getScreenSize();
                int height = serverPage.getHeight(),
                        width = serverPage.getWidth();
                serverPage.setLocation(
                        (frame.width - width) * 3 / 6,
                        (frame.height - height) / 2);
                serverPage.setVisible(true); 
            }
            else if(getCategoryName.equalsIgnoreCase("student")) {
                //
                tabbedHolder.addTab("  My Home", new welcomePanel());
                tabbedHolder.addTab("My CBT", new cbTestingInterfacePanel());
                //
                tabbedHolder.setForegroundAt(0, Color.BLUE);
                tabbedHolder.setBackgroundAt(0, themeColor);
            }
            //
            centerHolder.add(tabbedHolder, BorderLayout.CENTER);
            validate();
            //
            tabbedHolder.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                    if (tabbedHolder.getSelectedIndex() == 1) {
                        for (int x = 0; x < tabbedHolder.getTabCount(); x++) {
                            tabbedHolder.setForegroundAt(x, Color.BLACK);
                        }
                        tabbedHolder.setBackgroundAt(1, themeColor);
                        tabbedHolder.setForegroundAt(1, Color.BLUE);
                    } else if (tabbedHolder.getSelectedIndex() == 2) {
                        for (int x = 0; x < tabbedHolder.getTabCount(); x++) {
                            tabbedHolder.setForegroundAt(x, Color.BLACK);
                        }
                        tabbedHolder.setBackgroundAt(2, themeColor);
                        tabbedHolder.setForegroundAt(2, Color.BLUE);
                    } else if (tabbedHolder.getSelectedIndex() == 3) {
                        for (int x = 0; x < tabbedHolder.getTabCount(); x++) {
                            tabbedHolder.setForegroundAt(x, Color.BLACK);
                        }
                        tabbedHolder.setBackgroundAt(3, themeColor);
                        tabbedHolder.setForegroundAt(3, Color.BLUE);
                    } else if (tabbedHolder.getSelectedIndex() == 4) {
                        for (int x = 0; x < tabbedHolder.getTabCount(); x++) {
                            tabbedHolder.setForegroundAt(x, Color.BLACK);
                        }
                        tabbedHolder.setBackgroundAt(4, themeColor);
                        tabbedHolder.setForegroundAt(4, Color.BLUE);
                    } else if (tabbedHolder.getSelectedIndex() == 5) {
                        for (int x = 0; x < tabbedHolder.getTabCount(); x++) {
                            tabbedHolder.setForegroundAt(x, Color.BLACK);
                        }
                        tabbedHolder.setBackgroundAt(5, themeColor);
                        tabbedHolder.setForegroundAt(5, Color.BLUE);
                    } else if (tabbedHolder.getSelectedIndex() == 6) {
                        for (int x = 0; x < tabbedHolder.getTabCount(); x++) {
                            tabbedHolder.setForegroundAt(x, Color.BLACK);
                        }
                        tabbedHolder.setBackgroundAt(6, themeColor);
                        tabbedHolder.setForegroundAt(6, Color.BLUE);
                    } else if (tabbedHolder.getSelectedIndex() == 7) {
                        for (int x = 0; x < tabbedHolder.getTabCount(); x++) {
                            tabbedHolder.setForegroundAt(x, Color.BLACK);
                        }
                        tabbedHolder.setBackgroundAt(7, themeColor);
                        tabbedHolder.setForegroundAt(7, Color.BLUE);
                    } else if (tabbedHolder.getSelectedIndex() == 8) {
                        for (int x = 0; x < tabbedHolder.getTabCount(); x++) {
                            tabbedHolder.setForegroundAt(x, Color.BLACK);
                        }
                        tabbedHolder.setBackgroundAt(8, themeColor);
                        tabbedHolder.setForegroundAt(8, Color.BLUE);
                    } else if (tabbedHolder.getSelectedIndex() == 9) {
                        for (int x = 0; x < tabbedHolder.getTabCount(); x++) {
                            tabbedHolder.setForegroundAt(x, Color.BLACK);
                        }
                        tabbedHolder.setBackgroundAt(9, themeColor);
                        tabbedHolder.setForegroundAt(9, Color.BLUE);
                    } else if (tabbedHolder.getSelectedIndex() == 10) {
                        for (int x = 0; x < tabbedHolder.getTabCount(); x++) {
                            tabbedHolder.setForegroundAt(x, Color.BLACK);
                        }
                        tabbedHolder.setBackgroundAt(10, themeColor);
                        tabbedHolder.setForegroundAt(10, Color.BLUE);
                    } else if (tabbedHolder.getSelectedIndex() == 11) {
                        for (int x = 0; x < tabbedHolder.getTabCount(); x++) {
                            tabbedHolder.setForegroundAt(x, Color.BLACK);
                        }
                        tabbedHolder.setBackgroundAt(11, themeColor);
                        tabbedHolder.setForegroundAt(11, Color.BLUE);
                    } else {
                        for (int x = 1; x < tabbedHolder.getTabCount(); x++) {
                            tabbedHolder.setForegroundAt(x, Color.BLACK);
                        }
                        tabbedHolder.setBackgroundAt(0, themeColor);
                        tabbedHolder.setForegroundAt(0, Color.BLUE);
                    }
                }
            });
        }
        //
        class serverConfigPage extends JDialog {
            //
            JLabel headerLab = new JLabel(" Enter This Computer IP");
            JTextField serverField = new JTextField(15);
            JButton serverButton = new JButton("Config Now");
            JTextArea msg = new JTextArea(7, 50);
            //
            public serverConfigPage() {
                //
                JPanel serverSouthPanel = new JPanel(),
                        serverNorthPanel = new JPanel();
                //
                msg.setText("To Retreive The Correct Server IP Address.... \n\n"
                        + "1. Click Windows Start Icon\n"
                        + "2. Type CMD On Search Program And Files And Click CMD On The List\n"
                        + "3. On The CMD Display Environment, Type ipconfig And Press Enter\n"
                        + "5. The IP Address Of The Computer (Server) Is "
                        + "In The Format: 'XXX.XXX.XXX.XXX' Starting With '192.168...'\n"
                        + "6. Copy And Use As Server IP Address\n\n"
                        + "Please Click OK To Continue!");
                //
                msg.setLineWrap(true);
                msg.setWrapStyleWord(true);
                msg.setEditable(false);
                msg.setBorder(BorderFactory.createEtchedBorder());
                msg.setFont(new Font(getFontType, Font.PLAIN, 14));
                msg.setForeground(Color.BLUE);
                //
                JOptionPane.showMessageDialog(null, 
                        new JScrollPane(msg),
                        getSchoolName.toUpperCase(),
                        JOptionPane.PLAIN_MESSAGE);
                //
                serverNorthPanel.setBackground(Color.WHITE);
                serverNorthPanel.add(new JLabel(
                        new ImageIcon("images/serverlogo.jpg")));
                //
                headerLab.setFont(new Font(getFontType, Font.PLAIN, 14));
                headerLab.setForeground(Color.BLUE);
                serverField.setFont(new Font(getFontType, Font.PLAIN, 14));
                serverField.setForeground(Color.BLACK);
                serverField.setBorder(BorderFactory.createEtchedBorder());
                serverButton.setFont(new Font(getFontType, Font.BOLD, 10));
                serverButton.setForeground(Color.RED);
                serverButton.setBackground(Color.WHITE);
                //
                JPanel serverButtonPanel = new JPanel();
                serverButtonPanel.setBackground(Color.WHITE);
                serverButtonPanel.add(serverButton);
                //
                serverSouthPanel.setBackground(Color.WHITE);
                serverSouthPanel.setLayout(new GridLayout(3, 1, 0, 2));
                serverSouthPanel.add(headerLab);
                serverSouthPanel.add(serverField);
                serverSouthPanel.add(serverButtonPanel);
                //
                setBackground(Color.WHITE);
                setLayout(new BorderLayout());
                add(serverNorthPanel, BorderLayout.CENTER);
                add(serverSouthPanel, BorderLayout.SOUTH);
                validate();
                //
                serverButton.addActionListener(new configServerControl());
            }
            //
            class configServerControl implements ActionListener {
                
                public void actionPerformed(ActionEvent e) {
                    getServerIP = serverField.getText();
                    //
                    if(getLoginUserName.equalsIgnoreCase("admin")) {
                        //
                        if(getServerIP.equalsIgnoreCase("")) {
                            //
                            msg.setText("Server IP Format Not Recognized. "
                                    + "To Retreive The Correct Server IP Address.... \n\n"
                                    + "1. Click Windows Start Icon\n"
                                    + "2. Type CMD On Search Program And Files And Click CMD On The List\n"
                                    + "3. On The CMD Display Environment, Type ipconfig And Press Enter\n"
                                    + "5. The IP Address Of The Computer (Server) Is "
                                    + "In The Format: 'XXX.XXX.XXX.XXX' Starting With '192.168...'\n"
                                    + "6. Copy And Use As Server IP Address\n\n"
                                    + "Please Enter Server IP!");
                            //
                            JOptionPane.showMessageDialog(null, 
                                    new JScrollPane(msg),
                                    getSchoolName.toUpperCase(),
                                    JOptionPane.PLAIN_MESSAGE);
                        }
                        else {
                            //
                            int dotCounter = 0;
                            //
                            for(int pos = 0; pos < getServerIP.length(); pos++) {
                                if(getServerIP.charAt(pos) == '.') {
                                    dotCounter++;
                                }
                            }
                            //
                            if(dotCounter < 3 || 
                                    getServerIP.length() == 0) {
                                //
                                msg.setText("Server IP Format Not Recognized. "
                                        + "To Retreive The Correct Server IP Address.... \n\n"
                                        + "1. Click Windows Start Icon\n"
                                        + "2. Type CMD On Search Program And Files And Click CMD On The List\n"
                                        + "3. On The CMD Display Environment, Type ipconfig And Press Enter\n"
                                        + "5. The IP Address Of The Computer (Server) Is "
                                        + "In The Format: 'XXX.XXX.XXX.XXX' Starting With '192.168...'\n"
                                        + "6. Copy And Use As Server IP Address\n\n"
                                        + "Please Enter Server IP");
                                //
                                JOptionPane.showMessageDialog(null, 
                                        new JScrollPane(msg),
                                        getSchoolName.toUpperCase(),
                                        JOptionPane.PLAIN_MESSAGE);
                            }
                            else {
                                serverPage.setVisible(false);
                                //
                                if(dotCounter == 3) {
                                    if(getLoginUserName.equalsIgnoreCase("admin")) {
                                        //
                                        tabbedHolder.addTab("  My Home", new welcomePanel());
                                        tabbedHolder.addTab("        Questions Designer", new questionInterfacePanel());
                                        tabbedHolder.addTab("My CBT", new cbTestingSetupPanel());
                                        //
                                        tabbedHolder.setForegroundAt(0, Color.BLUE);
                                        tabbedHolder.setBackgroundAt(0, themeColor);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        //
        class cbTestingInterfacePanel extends JPanel {
            
            JPanel northPanel = new JPanel(),
                    southPanel = new JPanel(),
                    centerPanel = new JPanel();
            //
            JPanel formPicturePanel = new JPanel(),
                    testingInterfaceDisplayPanel = new JPanel();
            //
            JPanel questionOptionPanel = new JPanel();
            //
            JPanel buttonPanel = new JPanel(),
                    centbuttonPanel = new JPanel(),
                    centFormPicturePanel = new JPanel(),
                    centFormPanel = new JPanel();
            //
            String[] viewTestClassList = null,
                    viewTestStudentList = null,
                    viewTestSubjectList = null,
                    viewTestYearList = null,
                    viewTestTermList = null,
                    viewTestQuestionModeList = {"OBJECTIVE", "THEORY"};
            //
            JComboBox classBox = null,
                    termBox = null,
                    subjectBox = null, 
                    questionModeBox = new JComboBox(viewTestQuestionModeList),
                    yearBox = null;
            //
            String getTestClass = "",
                    getTestTerm = "",
                    getTestSubject = "",
                    getQuestionMode = "",
                    getTestSession = "",
                    getTestScheduleAS = "",
                    getQuestionBatch = "";//eval the populat
            //
            String[] questionIDTrk = null,
                    studentAnsweredOption = null;
            //
            public cbTestingInterfacePanel() {
                //
                southPanel.setBackground(themeColor);
                centerPanel.setBackground(themeColor);
                northPanel.setBackground(themeColor);
                northPanel.setBorder(BorderFactory.createEtchedBorder());
                //
                JLabel loginDetails = new JLabel("Login As: "
                        + getLoginUserName.toUpperCase() + "   ");
                loginDetails.setFont(new Font(getFontType, Font.BOLD, 14));
                loginDetails.setForeground(Color.ORANGE);
                //
                JPanel optionPanel = new JPanel();
                optionPanel.setLayout(new BorderLayout());
                optionPanel.setBackground(themeColor);
                optionPanel.setBorder(BorderFactory.createEtchedBorder());
                optionPanel.add(loginDetails, BorderLayout.EAST);
                //
                northPanel.setLayout(new BorderLayout());
                northPanel.add(optionPanel, BorderLayout.NORTH);
                northPanel.add(new JLabel(" "), BorderLayout.SOUTH);
                //
                testingInterfaceDisplayPanel.setLayout(new BorderLayout());
                testingInterfaceDisplayPanel.setBackground(themeColor);
                //
                centerPanel.setLayout(new BorderLayout());
                centerPanel.add(testingInterfaceDisplayPanel, BorderLayout.CENTER);
                //
                setLayout(new BorderLayout());
                add(northPanel, BorderLayout.NORTH);
                add(centerPanel, BorderLayout.CENTER);
                validate();
                //
                new newStudentTestControl();
            }
            //
            class newStudentTestControl {

                String[] questionParaList = {"SELECT TERM:  ", "SELECT CLASS:  ", 
                                "SELECT SESSION/YEAR:  ", "SELECT QUESTION MODE:  ", 
                                "SELECT SUBJECT:  ", 
                                "REG NUMBER OR STUDENT NAME:  "};
                //
                JLabel[] questionParaLab = new JLabel[questionParaList.length];
                //
                String[] classParaList = null,
                        termParaList = null,
                        sessionParaList = null,
                        subjectParaList = null,
                        questionParaModeList = {"OBJECTIVE", "THEORY"};
                //
                JComboBox classParaBox = null,
                        termParaBox = null,
                        sessionParaBox = null,
                        subjectParaBox = null,
                        questionParaModeBox = new JComboBox(questionParaModeList);
                //
                JTextField registrationNumberField = new JTextField(15);
                //
                JButton continueParaButton = new JButton("Login To DashBoard",
                        new ImageIcon("images/next.jpg"));
                //
                private JPanel formPanel = new JPanel();
                //
                JLabel messageDisplayLab = new JLabel(" Enter Info & Click Login.");
                //
                public newStudentTestControl() {
                    //
                    formPanel.removeAll();
                    formPicturePanel.removeAll();
                    centFormPanel.removeAll();
                    buttonPanel.removeAll();
                    centFormPicturePanel.removeAll();
                    centbuttonPanel.removeAll();
                    //
                    int ct = getNumberOfDetails("select distinct setupclass "
                            + "from loadtestprofile");
                    if (ct == 0) {
                        classParaBox = new JComboBox();
                    } 
                    else {
                        classParaList = populateList("select distinct setupclass "
                                + "from loadtestprofile "
                                + "order by setupclass", ct);
                        //
                        for (int pos = 0; pos < classParaList.length; pos++) {
                            classParaList[pos] = classParaList[pos].toUpperCase();
                        }
                        classParaBox = new JComboBox(classParaList);
                    }
                    //
                    ct = getNumberOfDetails("select distinct setupterm "
                            + "from loadtestprofile");
                    if (ct == 0) {
                        termParaBox = new JComboBox();
                    } 
                    else {
                        termParaList = populateList("select distinct setupterm "
                                + "from loadtestprofile "
                                + "order by setupterm", ct);
                        //
                        for (int pos = 0; pos < termParaList.length; pos++) {
                            termParaList[pos] = termParaList[pos].toUpperCase();
                        }
                        termParaBox = new JComboBox(termParaList);
                    }
                    //
                    ct = getNumberOfDetails("select distinct setupsession "
                            + "from loadtestprofile");
                    if (ct == 0) {
                        sessionParaBox = new JComboBox();
                    } 
                    else {
                        sessionParaList = 
                                populateList("select distinct setupsession "
                                + "from loadtestprofile "
                                + "order by setupsession", ct);
                        //
                        sessionParaBox = new JComboBox(sessionParaList);
                    }
                    //
                    ct = getNumberOfDetails("select distinct questionsubject "
                            + "from questionprofile");
                    if (ct == 0) {
                        subjectParaBox = new JComboBox();
                    } 
                    else {
                        subjectParaList = 
                                populateList("select distinct questionsubject "
                                + "from questionprofile "
                                + "order by questionsubject", ct);
                        //
                        for (int pos = 0; pos < subjectParaList.length; pos++) {
                            subjectParaList[pos] = subjectParaList[pos].toUpperCase();
                        }
                        subjectParaBox = new JComboBox(subjectParaList);
                    }
                    //
                    formPanel.setLayout(new GridLayout(questionParaList.length, 2, 0, 5));
                    formPanel.setBorder(
                            BorderFactory.createTitledBorder(null,
                            " Student Test Generation Parameter ",
                            TitledBorder.LEFT, 0, new Font(getFontType, Font.BOLD, 10), 
                            Color.BLUE));
                    //
                    ct = 0;
                    for (int pos = 0; pos < questionParaList.length; pos++) {
                        questionParaLab[pos] = new JLabel(questionParaList[pos], JLabel.RIGHT);
                        questionParaLab[pos].setFont(new Font(getFontType, Font.BOLD, 14));
                        questionParaLab[pos].setForeground(Color.RED);
                    }
                    //
                    formPanel.add(questionParaLab[0]);
                    formPanel.add(termParaBox);
                    //
                    formPanel.add(questionParaLab[1]);
                    formPanel.add(classParaBox);
                    //
                    formPanel.add(questionParaLab[2]);
                    formPanel.add(sessionParaBox);
                    //
                    formPanel.add(questionParaLab[3]);
                    formPanel.add(questionParaModeBox);
                    //
                    formPanel.add(questionParaLab[4]);
                    formPanel.add(subjectParaBox);
                    //
                    formPanel.add(questionParaLab[5]);
                    formPanel.add(registrationNumberField);
                    //
                    registrationNumberField.setFont(new Font(getFontType, Font.BOLD, 14));
                    registrationNumberField.setForeground(Color.BLACK);
                    registrationNumberField.setBorder(BorderFactory.createEtchedBorder());
                    //
                    classParaBox.setFont(new Font(getFontType, Font.PLAIN, 12));
                    classParaBox.setForeground(Color.BLACK);
                    termParaBox.setFont(new Font(getFontType, Font.PLAIN, 12));
                    termParaBox.setForeground(Color.BLACK);
                    subjectParaBox.setFont(new Font(getFontType, Font.PLAIN, 12));
                    subjectParaBox.setForeground(Color.BLACK);
                    sessionParaBox.setFont(new Font(getFontType, Font.PLAIN, 12));
                    sessionParaBox.setForeground(Color.BLACK);
                    questionParaModeBox.setFont(new Font(getFontType, Font.PLAIN, 12));
                    questionParaModeBox.setForeground(Color.BLACK);
                    //
                    formPicturePanel.add(formPanel);
                    //
                    continueParaButton.setVerticalTextPosition(AbstractButton.CENTER);
                    continueParaButton.setHorizontalTextPosition(AbstractButton.CENTER);
                    continueParaButton.setFont(new Font(getFontType, Font.BOLD, 10));
                    continueParaButton.setForeground(Color.RED);
                    continueParaButton.setBackground(Color.WHITE);
                    //
                    messageDisplayLab.setFont(new Font(getFontType, Font.PLAIN, 12));
                    messageDisplayLab.setForeground(Color.BLACK);
                    //
                    buttonPanel.setLayout(new FlowLayout());
                    buttonPanel.setBackground(Color.WHITE);
                    buttonPanel.add(continueParaButton);
                    //
                    centbuttonPanel.setBackground(Color.WHITE);
                    centbuttonPanel.setLayout(new BorderLayout());
                    centbuttonPanel.setBorder(BorderFactory.createEtchedBorder());
                    centbuttonPanel.add(buttonPanel, BorderLayout.CENTER);
                    centbuttonPanel.add(messageDisplayLab, BorderLayout.WEST);
                    //
                    centFormPicturePanel.setLayout(new BorderLayout());
                    centFormPicturePanel.add(formPicturePanel, BorderLayout.WEST);
                    centFormPicturePanel.add(centbuttonPanel, BorderLayout.SOUTH);
                    //
                    centFormPanel.setLayout(new BorderLayout());
                    centFormPanel.add(centFormPicturePanel, BorderLayout.NORTH);
                    //
                    northPanel.add(centFormPanel, BorderLayout.CENTER);
                    validate();
                    //
                    formPicturePanel.setBackground(themeColor);
                    formPanel.setBackground(themeColor);
                    centFormPicturePanel.setBackground(themeColor);
                    centFormPanel.setBackground(themeColor);
                    //
                    sessionParaBox.addActionListener(new selectSessionControl());
                    classParaBox.addActionListener(new selectClassControl());
                    termParaBox.addActionListener(new selectTermControl());
                    subjectParaBox.addActionListener(new selectSubjectControl());
                    questionParaModeBox.addActionListener(new questionModeControl());
                    continueParaButton.addActionListener(new submitTestInfoControl());
                }
                //
                class questionModeControl implements ActionListener {

                    public void actionPerformed(ActionEvent e) {
                        for(int pos = 0; pos < questionParaModeList.length; pos++) {
                            if(pos == questionParaModeBox.getSelectedIndex()) {
                                getQuestionMode = questionParaModeList[pos].toLowerCase();
                            }
                        }
                        //
                        int ct = 0;
                        if(getQuestionMode.equalsIgnoreCase("objective")) {
                            ct = getNumberOfDetails("select distinct questionsubject "
                                        + "from questionprofile");
                            if (ct > 0) {
                                subjectParaList = 
                                        populateList("select distinct questionsubject "
                                        + "from questionprofile "
                                        + "order by questionsubject", ct);
                                //
                                subjectParaBox.removeAllItems();
                                for (int pos = 0; pos < subjectParaList.length; pos++) {
                                    subjectParaBox.addItem(
                                            subjectParaList[pos].toString().toUpperCase());
                                }
                            }
                        }
                        else if(getQuestionMode.equalsIgnoreCase("theory")) {
                            ct = getNumberOfDetails("select distinct questionsubject "
                                        + "from theoryquestionprofile");
                            if (ct > 0) {
                                subjectParaList = 
                                        populateList("select distinct questionsubject "
                                        + "from theoryquestionprofile "
                                        + "order by questionsubject", ct);
                                //
                                subjectParaBox.removeAllItems();
                                for (int pos = 0; pos < subjectParaList.length; pos++) {
                                    subjectParaBox.addItem(
                                            subjectParaList[pos].toString().toUpperCase());
                                }
                            }
                        }
                        //
                        getTestSubject = "";
                    }
                }
                //
                class submitTestInfoControl implements ActionListener {

                    public void actionPerformed(ActionEvent e) {
                        if(getTestClass.equalsIgnoreCase("") ||
                            getTestTerm.equalsIgnoreCase("") ||
                            getTestSession.equalsIgnoreCase("") ||
                            getTestSubject.equalsIgnoreCase("") ||
                            getQuestionMode.equalsIgnoreCase("") ||
                            registrationNumberField.getText().
                                equalsIgnoreCase("")) {
                            //
                            promptLab.setText("Empty field Detected");
                            JOptionPane.showMessageDialog(null, 
                                    promptLab,
                                    getSchoolName.toUpperCase(),
                                    JOptionPane.INFORMATION_MESSAGE,
                                    infoIcon);
                        }
                        else {
                            //
                            getTestScheduleAS = 
                                    getOneDetail("select practiceorexam from stoptestprofile "
                                    + "where setupclass = '" + getTestClass + "' "
                                    + "and setupterm = '" + getTestTerm + "' "
                                    + "and setupsession = '" + getTestSession + "' "
                                    + "and questionmode = '" + getQuestionMode + "' "
                                    + "and setupsubject = '" + getTestSubject + "'");
                            //
                            if(getQuestionMode.equalsIgnoreCase("objective")) {
                                if(getNumberOfDetails("select * from loadtestprofile "
                                        + "where setupclass = '" + getTestClass + "' "
                                        + "and setupterm = '" + getTestTerm + "' "
                                        + "and setupsession = '" + getTestSession + "' "
                                        + "and questionmode = '" + getQuestionMode + "' "
                                        + "and setupsubject = '" + getTestSubject + "'") == 0) {
                                    //
                                    promptLab.setText("Sorry! No Question Batch Prepared For "
                                            + "Selected Parameters");
                                    JOptionPane.showMessageDialog(null, 
                                            promptLab,
                                            getSchoolName.toUpperCase(),
                                            JOptionPane.WARNING_MESSAGE,
                                            warningIcon);
                                }
                                else if(getNumberOfDetails("select * from testscoresprofile "
                                        + "where testclass = '" + getTestClass + "' "
                                        + "and testterm = '" + getTestTerm + "' "
                                        + "and testsession = '" + getTestSession + "' "
                                        + "and testsubject = '" + getTestSubject + "' "
                                        + "and questionmode = '" + getQuestionMode + "' "
                                        + "and registrationnumber = '" + 
                                        registrationNumberField.getText().toLowerCase() + "'") > 0) {
                                    //
                                    promptLab.setText("Sorry! You Have Already Completed Your "
                                            + "Test Using Selected Parameters.");
                                    JOptionPane.showMessageDialog(null, 
                                            promptLab,
                                            getSchoolName.toUpperCase(),
                                            JOptionPane.WARNING_MESSAGE,
                                            warningIcon);
                                }
                                else {
                                    //
                                    JTextArea instructionArea = new JTextArea(15, 60);
                                    instructionArea.setForeground(Color.BLUE);
                                    instructionArea.setFont(new Font("canbria", Font.PLAIN, 14));
                                    instructionArea.setEditable(false);
                                    instructionArea.setLineWrap(true);
                                    instructionArea.setWrapStyleWord(true);
                                    //
                                    instructionArea.setText("Welcome To " + 
                                            getSchoolName.toUpperCase() + " CBT Service.\n"
                                            + "-------------------------------------------"
                                            + "-------------------------------------------"
                                            + "---------------------------------------\n"
                                            + "INSTRUCTIONS =>\n"
                                            + "-------------------------------------------"
                                            + "-------------------------------------------"
                                            + "---------------------------------------\n"
                                            + "o) Your Questions Will Be Generated For You "
                                            + "On The Specific Subject You Selected.\n"
                                            + "o) Your Timer Will Start Immediately After Question Generation. "
                                            + "Your Timer Is Configured In Minutes And Will "
                                            + "Countdown Accordingly.\n"
                                            + "o) Click Next Question Button To Go To Next Question\n"
                                            + "o) Click Prev Question Button To Return To Previous Question\n"
                                            + "o) Click Submit Test To End Test After All Attempts Has Being Done "
                                            + "And Submit There Unto.\n\n"
                                            + "Do You Want To Continue With Test?");
                                    //
                                    int rep = JOptionPane.showConfirmDialog(null,
                                            new JScrollPane(instructionArea),
                                            getSchoolName.toUpperCase(),
                                            JOptionPane.YES_OPTION,
                                            JOptionPane.PLAIN_MESSAGE);
                                    //
                                    if(rep == JOptionPane.YES_OPTION) {
                                        if(getTestScheduleAS.equalsIgnoreCase("ca/examination")) {
                                            int count = (getNumberOfDetails("select * "
                                                            + "from testscoresprofile") + 1);
                                            //
                                            insertUpdateDelete("insert into testscoresprofile "
                                                + "values ('" + count + "', "
                                                        + "'" + registrationNumberField.getText().toLowerCase() + "', "
                                                        + "'" + getTestSubject + "', "
                                                        + "'" + getTestClass + "', "
                                                        + "'" + getTestTerm + "', "
                                                        + "'" + getTestSession + "', "
                                                        + "'in progress..', "
                                                        + "'in progress..', "
                                                        + "'" + getQuestionMode + "', "
                                                        + "'" + (day + " " + 
                                                    letterMonth(Integer.parseInt(mon)).toLowerCase() + 
                                                    " 20" + year) + "')");
                                        }
                                        //
                                        new testQuestionDomain();
                                    }
                                }
                            }
                            else if(getQuestionMode.equalsIgnoreCase("theory")) {
                                if(getNumberOfDetails("select * from loadtestprofile "
                                        + "where setupclass = '" + getTestClass + "' "
                                        + "and setupterm = '" + getTestTerm + "' "
                                        + "and setupsession = '" + getTestSession + "' "
                                        + "and questionmode = '" + getQuestionMode + "' "
                                        + "and setupsubject = '" + getTestSubject + "'") == 0) {
                                    //
                                    promptLab.setText("Sorry! No Question Batch Prepared For "
                                            + "Selected Parameters");
                                    JOptionPane.showMessageDialog(null, 
                                            promptLab,
                                            getSchoolName.toUpperCase(),
                                            JOptionPane.WARNING_MESSAGE,
                                            warningIcon);
                                }
                                else if(getNumberOfDetails("select * from testscoresprofile "
                                        + "where testclass = '" + getTestClass + "' "
                                        + "and testterm = '" + getTestTerm + "' "
                                        + "and testsession = '" + getTestSession + "' "
                                        + "and testsubject = '" + getTestSubject + "' "
                                        + "and questionmode = '" + getQuestionMode + "' "
                                        + "and registrationnumber = '" + 
                                        registrationNumberField.getText().toLowerCase() + "'") > 0) {
                                    //
                                    promptLab.setText("Sorry! You Have Already Completed Your "
                                            + "Test Using Selected Parameters.");
                                    JOptionPane.showMessageDialog(null, 
                                            promptLab,
                                            getSchoolName.toUpperCase(),
                                            JOptionPane.WARNING_MESSAGE,
                                            warningIcon);
                                }
                                else {
                                    //
                                    JTextArea instructionArea = new JTextArea(15, 60);
                                    instructionArea.setForeground(Color.BLUE);
                                    instructionArea.setFont(new Font("canbria", Font.PLAIN, 14));
                                    instructionArea.setEditable(false);
                                    instructionArea.setLineWrap(true);
                                    instructionArea.setWrapStyleWord(true);
                                    //
                                    instructionArea.setText("Welcome To " + 
                                            getSchoolName.toUpperCase() + " CBT Service.\n"
                                            + "-------------------------------------------"
                                            + "-------------------------------------------"
                                            + "---------------------------------------\n"
                                            + "INSTRUCTIONS =>\n"
                                            + "-------------------------------------------"
                                            + "-------------------------------------------"
                                            + "---------------------------------------\n"
                                            + "o) Your Questions Will Be Populated For You "
                                            + "On The Specific Subject You Selected.\n"
                                            + "o) Your Timer Will Start Immediately After Question Generation. "
                                            + "Your Timer Is Configured In Minutes And Will "
                                            + "Countdown Smoothly.\n"
                                            + "o) Click Next Question Button To Go To Next Question\n"
                                            + "o) Click Prev Question Button To Return To Previous Question\n"
                                            + "o) Click Submit Test To End Test And Submit There Unto.\n\n"
                                            + "Do You Want To Continue With Test?");
                                    //
                                    int rep = JOptionPane.showConfirmDialog(null,
                                            new JScrollPane(instructionArea),
                                            getSchoolName.toUpperCase(),
                                            JOptionPane.YES_OPTION,
                                            JOptionPane.PLAIN_MESSAGE);
                                    //
                                    if(rep == JOptionPane.YES_OPTION) {
                                        int count = (getNumberOfDetails("select * "
                                                + "from testscoresprofile") + 1);
                                        //
                                        insertUpdateDelete("insert into testscoresprofile "
                                            + "values ('" + count + "', "
                                                    + "'" + registrationNumberField.getText().toLowerCase() + "', "
                                                    + "'" + getTestSubject + "', "
                                                    + "'" + getTestClass + "', "
                                                    + "'" + getTestTerm + "', "
                                                    + "'" + getTestSession + "', "
                                                    + "'theory', "
                                                    + "'theory', "
                                                    + "'" + getQuestionMode + "', "
                                                    + "'" + (day + " " + 
                                                letterMonth(Integer.parseInt(mon)).toLowerCase() + 
                                                " 20" + year) + "')");
                                        //
                                        new testQuestionDomain();
                                    }
                                }
                            }
                        }
                    }
                }
                //
                class selectClassControl implements ActionListener {

                    public void actionPerformed(ActionEvent e) {
                        for (int x = 0; x < classParaList.length; x++) {
                            if (x == classParaBox.getSelectedIndex()) {
                                getTestClass = classParaList[x].toLowerCase();
                            }
                        }
                    }
                }
                //
                class selectSubjectControl implements ActionListener {

                    public void actionPerformed(ActionEvent e) {
                        for (int x = 0; x < subjectParaList.length; x++) {
                            if (x == subjectParaBox.getSelectedIndex()) {
                                getTestSubject = subjectParaList[x].toLowerCase();
                            }
                        }
                    }
                }
                //
                class selectSessionControl implements ActionListener {

                    public void actionPerformed(ActionEvent e) {
                        for (int x = 0; x < sessionParaList.length; x++) {
                            if (x == sessionParaBox.getSelectedIndex()) {
                                getTestSession = sessionParaList[x];
                            }
                        }
                    }
                }
                //
                class selectTermControl implements ActionListener {

                    public void actionPerformed(ActionEvent e) {
                        for (int x = 0; x < termParaList.length; x++) {
                            if (x == termParaBox.getSelectedIndex()) {
                                getTestTerm = termParaList[x].toLowerCase();
                            }
                        }
                    }
                }
                //
                class testQuestionDomain {
                    //
                    int answeredQuestionCounter = 0;
                    //
                    JLabel currentTestSubjectLab = new JLabel(" Current Test Subject: " + 
                            getTestSubject.toUpperCase()),
                            studentRegistrationLab = new JLabel(" Student Registration Number: " + 
                            registrationNumberField.getText().toUpperCase()),
                            testTimeLab = new JLabel("Test Time: "),
                            totalQuestionLab = new JLabel("Total Questions  ", JLabel.RIGHT),
                            totalAnsweredLab = new JLabel(answeredQuestionCounter + 
                            " Question(s) Answered  ", JLabel.RIGHT), 
                            //
                            timeTextLab = new JLabel("MINUTES    SECONDS", JLabel.CENTER);
                    //
                    JTextField timeField = new JTextField(5);
                    //
                    boolean timerControl = true;
                    //
                    JButton nextButton = new JButton("NEXT QUESTION", 
                            new ImageIcon("images/nextquestion.jpg")),
                           prevButton = new JButton("PREV QUESTION", 
                            new ImageIcon("images/prevquestion.jpg")),
                           saveTheoryButton = new JButton("SAVE RESPONSE", 
                            new ImageIcon("images/savetheory.gif")), 
                           submitButton = new JButton("SUBMIT TEST", 
                            new ImageIcon("images/startlogo.jpg"));
                    //
                    JPanel centQuestionSectionPanel = new JPanel(),
                            centOptionSectionPanel = new JPanel(),
                            questionSectionPanel = new JPanel(),
                            optionSectionPanel = new JPanel(),
                            //
                            smartQuestionNumberPanel = new JPanel(),
                            //
                            optionAPanel = new JPanel(),
                            optionBPanel = new JPanel(),
                            optionCPanel = new JPanel(),
                            optionDPanel = new JPanel(),
                            optionEPanel = new JPanel();
                    //
                    JTextField[] smartQuestionField = null;
                    //
                    JRadioButton optionARadioButton = new JRadioButton("A"),
                            optionBRadioButton = new JRadioButton("B"),
                            optionCRadioButton = new JRadioButton("C"),
                            optionDRadioButton = new JRadioButton("D"),
                            optionERadioButton = new JRadioButton("E");
                    //
                    JLabel questionLab = new JLabel(""),
                           optionALab = new JLabel(""),
                           optionBLab = new JLabel(""),
                           optionCLab = new JLabel(""),
                           optionDLab = new JLabel(""),
                           optionELab = new JLabel("");
                    //
                    JTextArea questionLabArea = new JTextArea(30, 70),
                            theoryAnswerLabArea = new JTextArea("no response", 30, 70);
                    //
                    ArrayList questionArrayList = new ArrayList(),
                            answerOptionAArrayList = new ArrayList(),
                            answerOptionBArrayList = new ArrayList(),
                            answerOptionCArrayList = new ArrayList(),
                            answerOptionDArrayList = new ArrayList(),
                            answerOptionEArrayList = new ArrayList(),
                            correctOptionArrayList = new ArrayList(),
                            //
                            answerCollectorArrayList = new ArrayList();
                    //
                    int movementControl = 0;
                    String testDuration = "",
                            scorePerQuestion = "";
                    //
                    JTextField goByeLab = new JTextField("Please Quietly Leave The Hall", 
                            JTextField.CENTER);
                    JButton returnButton = 
                                new JButton("Return To Login Page");
                    //
                    public testQuestionDomain() {
                        //
                        questionArrayList.clear();
                        answerOptionAArrayList.clear();
                        answerOptionBArrayList.clear();
                        answerOptionCArrayList.clear();
                        answerOptionDArrayList.clear();
                        answerOptionEArrayList.clear();
                        correctOptionArrayList.clear();
                        //
                        new loadQuestionControl().start();
                    }
                    //
                    class loadQuestionControl extends Thread {
                        
                        public void run() {
                            //
                            JLabel pleaseWaitLab = new JLabel("Initializing... Please Wait", 
                                    JLabel.CENTER);
                            //
                            if(getQuestionMode.equalsIgnoreCase("theory")) {
                                
                                String batchNumber = getOneDetail("select questionbatchnumber "
                                        + "from loadtestprofile "
                                        + "where setupclass = '" + getTestClass + "' "
                                        + "and setupterm = '" + getTestTerm + "' "
                                        + "and questionmode = '" + getQuestionMode + "' "
                                        + "and setupsession = '" + getTestSession + "' "
                                        + "and setupsubject = '" + getTestSubject + "'");
                                //
                                int ct = getNumberOfDetails("select * from theoryquestionprofile "
                                        + "where questionclass = '" + getTestClass + "' "
                                        + "and questionterm = '" + getTestTerm + "' "
                                        + "and questionsession = '" + getTestSession + "' "
                                        + "and questionmode = '" + getQuestionMode + "' "
                                        + "and questionsubject = '" + getTestSubject + "' "
                                        + "and questionbatchnumber = '" + batchNumber + "'");
                                //
                                String[] logList = 
                                        populateList("select questionid from theoryquestionprofile "
                                        + "where questionclass = '" + getTestClass + "' "
                                        + "and questionterm = '" + getTestTerm + "' "
                                        + "and questionsession = '" + getTestSession + "' "
                                        + "and questionsubject = '" + getTestSubject + "' "
                                        + "and questionmode = '" + getQuestionMode + "' "
                                        + "and questionbatchnumber = '" + batchNumber + "'", ct);
                                //
                                pleaseWaitLab.setFont(new Font("canbria", Font.BOLD, 30));
                                //
                                JPanel waitLogoPanel = new JPanel();
                                waitLogoPanel.setBackground(Color.WHITE);
                                waitLogoPanel.setLayout(new GridLayout(2, 1, 0, -40));
                                waitLogoPanel.add(new JLabel(
                                        new ImageIcon("images/loadicon.gif")), JLabel.CENTER);
                                waitLogoPanel.add(pleaseWaitLab);
                                //
                                southPanel.removeAll();
                                optionSectionPanel.removeAll();
                                centQuestionSectionPanel.removeAll();
                                questionSectionPanel.removeAll();
                                centOptionSectionPanel.removeAll();
                                formPicturePanel.removeAll();
                                centFormPanel.removeAll();
                                buttonPanel.removeAll();
                                centFormPicturePanel.removeAll();
                                centbuttonPanel.removeAll();
                                northPanel.removeAll();
                                testingInterfaceDisplayPanel.removeAll();
                                //
                                testingInterfaceDisplayPanel.setLayout(new BorderLayout());
                                testingInterfaceDisplayPanel.add(waitLogoPanel, BorderLayout.CENTER);
                                validate();
                                //
                                String percentRater = "0.0",
                                        displayTxt = "0";
                                //
                                totalQuestionLab.setText(
                                        getOneDetail("select numberoftestquestions from loadtestprofile "
                                        + "where setupclass = '" + getTestClass + "' "
                                        + "and setupterm = '" + getTestTerm + "' "
                                        + "and setupsession = '" + getTestSession + "' "
                                        + "and questionmode = '" + getQuestionMode + "' "
                                        + "and setupsubject = '" + getTestSubject + "'"));
                                //
                                int actualQuestionCount = Integer.parseInt(totalQuestionLab.getText());
                                //actual schedule is greater than zero
                                if(actualQuestionCount > 0) {
                                    //
                                    if(actualQuestionCount > 50) {
                                        smartQuestionNumberPanel.setLayout(
                                            new GridLayout(2, ((int)(actualQuestionCount / 2) + 1), 
                                                2, 2));
                                    }
                                    else {
                                        smartQuestionNumberPanel.setLayout(
                                            new GridLayout(1, (int)((actualQuestionCount / 2) + 1), 
                                                2, 2));
                                    }
                                    //
                                    smartQuestionField = new JTextField[actualQuestionCount];
                                    //
                                    for(int pos = 0; pos < actualQuestionCount; pos++) {
                                        smartQuestionField[pos] = new JTextField("" + 
                                                (new String("" + (pos + 1)).length() == 1 ? 
                                                "0" + (pos + 1) : 
                                                (pos + 1)), 2);
                                        smartQuestionField[pos].setFont(new Font(getFontType, Font.BOLD, 10));
                                        smartQuestionField[pos].setBackground(Color.WHITE);
                                        smartQuestionField[pos].setEditable(false);
                                        smartQuestionField[pos].setHorizontalAlignment(JTextField.CENTER);
                                        smartQuestionField[pos].setBorder(BorderFactory.createEmptyBorder());
                                        smartQuestionField[pos].setCursor(
                                                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                                        smartQuestionField[pos].addMouseListener(
                                                new jumpToQuestionPageControl(pos));
                                        //
                                        smartQuestionNumberPanel.add(smartQuestionField[pos]);
                                    }
                                    //
                                    totalQuestionLab.setText("" + actualQuestionCount + " Total Questions ");
                                    //
                                    ArrayList randomCheckArrayList = randomPickerGenerator(ct, 
                                            actualQuestionCount);
                                    //
                                    double trk = actualQuestionCount;
                                    //
                                    for(int pos = 0; pos < actualQuestionCount; pos++) {
                                        //
                                        answerCollectorArrayList.add(pos, new String(""));
                                        //
                                        questionArrayList.add(pos, 
                                            getOneDetail("select questionbody from theoryquestionprofile "
                                                + "where questionclass = '" + getTestClass + "' "
                                                + "and questionterm = '" + getTestTerm + "' "
                                                + "and questionsession = '" + getTestSession + "' "
                                                + "and questionsubject = '" + getTestSubject + "' "
                                                + "and questionmode = '" + getQuestionMode + "' "
                                                + "and questionbatchnumber = '" + batchNumber + "' "
                                                + "and questionid = '" + logList[Integer.parseInt(
                                                randomCheckArrayList.get(pos).toString()) - 1] + "'").toString());
                                        //
                                        try{Thread.sleep(50);}catch(InterruptedException erer){}
                                        //
                                        pleaseWaitLab.setText("Loading Questions... "
                                                + "Please Wait (" + displayTxt + "%)");
                                        //
                                        percentRater = "" + 
                                                (Double.parseDouble(percentRater) + (100 / trk)) + "000";
                                        percentRater = percentRater.substring(0, percentRater.indexOf(".") + 3);
                                        displayTxt = percentRater.substring(0, percentRater.indexOf("."));
                                    }
                                }
                            }
                            else if(getQuestionMode.equalsIgnoreCase("objective")) {
                                //
                                String batchNumber = getOneDetail("select questionbatchnumber "
                                        + "from loadtestprofile "
                                        + "where setupclass = '" + getTestClass + "' "
                                        + "and setupterm = '" + getTestTerm + "' "
                                        + "and questionmode = '" + getQuestionMode + "' "
                                        + "and setupsession = '" + getTestSession + "' "
                                        + "and setupsubject = '" + getTestSubject + "'");
                                //
                                int ct = getNumberOfDetails("select * from questionprofile "
                                        + "where questionclass = '" + getTestClass + "' "
                                        + "and questionterm = '" + getTestTerm + "' "
                                        + "and questionsession = '" + getTestSession + "' "
                                        + "and questionmode = '" + getQuestionMode + "' "
                                        + "and questionsubject = '" + getTestSubject + "' "
                                        + "and questionbatchnumber = '" + batchNumber + "'");
                                //
                                String[] logList = 
                                        populateList("select questionid from questionprofile "
                                        + "where questionclass = '" + getTestClass + "' "
                                        + "and questionterm = '" + getTestTerm + "' "
                                        + "and questionsession = '" + getTestSession + "' "
                                        + "and questionsubject = '" + getTestSubject + "' "
                                        + "and questionmode = '" + getQuestionMode + "' "
                                        + "and questionbatchnumber = '" + batchNumber + "'", ct);
                                //
                                pleaseWaitLab.setFont(new Font("canbria", Font.BOLD, 30));
                                //
                                JPanel waitLogoPanel = new JPanel();
                                waitLogoPanel.setBackground(Color.WHITE);
                                waitLogoPanel.setLayout(new GridLayout(2, 1, 0, -40));
                                waitLogoPanel.add(new JLabel(
                                        new ImageIcon("images/loadicon.gif")), JLabel.CENTER);
                                waitLogoPanel.add(pleaseWaitLab);
                                //
                                southPanel.removeAll();
                                optionSectionPanel.removeAll();
                                centQuestionSectionPanel.removeAll();
                                questionSectionPanel.removeAll();
                                centOptionSectionPanel.removeAll();
                                optionAPanel.removeAll();
                                optionBPanel.removeAll();
                                optionCPanel.removeAll();
                                optionDPanel.removeAll();
                                optionEPanel.removeAll();
                                formPicturePanel.removeAll();
                                centFormPanel.removeAll();
                                buttonPanel.removeAll();
                                centFormPicturePanel.removeAll();
                                centbuttonPanel.removeAll();
                                northPanel.removeAll();
                                testingInterfaceDisplayPanel.removeAll();
                                //
                                testingInterfaceDisplayPanel.setLayout(new BorderLayout());
                                testingInterfaceDisplayPanel.add(waitLogoPanel, BorderLayout.CENTER);
                                validate();
                                //
                                String percentRater = "0.0",
                                        displayTxt = "0";
                                //
                                totalQuestionLab.setText(
                                        getOneDetail("select numberoftestquestions from loadtestprofile "
                                        + "where setupclass = '" + getTestClass + "' "
                                        + "and setupterm = '" + getTestTerm + "' "
                                        + "and setupsession = '" + getTestSession + "' "
                                        + "and questionmode = '" + getQuestionMode + "' "
                                        + "and setupsubject = '" + getTestSubject + "'"));
                                //
                                int actualQuestionCount = Integer.parseInt(totalQuestionLab.getText());
                                //actual schedule is greater than zero
                                if(actualQuestionCount > 0) {
                                    //
                                    if(actualQuestionCount > 50) {
                                        smartQuestionNumberPanel.setLayout(
                                            new GridLayout(2, ((int)(actualQuestionCount / 2) + 1), 
                                                2, 2));
                                    }
                                    else {
                                        smartQuestionNumberPanel.setLayout(
                                            new GridLayout(1, (int)((actualQuestionCount / 2) + 1), 
                                                2, 2));
                                    }
                                    //
                                    smartQuestionField = new JTextField[actualQuestionCount];
                                    //
                                    for(int pos = 0; pos < actualQuestionCount; pos++) {
                                        smartQuestionField[pos] = new JTextField("" + 
                                                (new String("" + (pos + 1)).length() == 1 ? 
                                                "0" + (pos + 1) : 
                                                (pos + 1)), 2);
                                        smartQuestionField[pos].setFont(new Font(getFontType, Font.BOLD, 10));
                                        smartQuestionField[pos].setBackground(Color.WHITE);
                                        smartQuestionField[pos].setEditable(false);
                                        smartQuestionField[pos].setHorizontalAlignment(JTextField.CENTER);
                                        smartQuestionField[pos].setBorder(BorderFactory.createEmptyBorder());
                                        smartQuestionField[pos].setCursor(
                                                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                                        smartQuestionField[pos].addMouseListener(
                                                new jumpToQuestionPageControl(pos));
                                        //
                                        smartQuestionNumberPanel.add(smartQuestionField[pos]);
                                    }
                                    //
                                    totalQuestionLab.setText("" + actualQuestionCount + " Total Questions ");
                                    //
                                    ArrayList randomCheckArrayList = randomPickerGenerator(ct, 
                                            actualQuestionCount);
                                    //
                                    double trk = actualQuestionCount;
                                    //
                                    for(int pos = 0; pos < actualQuestionCount; pos++) {
                                        //
                                        answerCollectorArrayList.add(pos, new String(""));
                                        //
                                        questionArrayList.add(pos, 
                                            getOneDetail("select questionbody from questionprofile "
                                                + "where questionclass = '" + getTestClass + "' "
                                                + "and questionterm = '" + getTestTerm + "' "
                                                + "and questionsession = '" + getTestSession + "' "
                                                + "and questionsubject = '" + getTestSubject + "' "
                                                + "and questionmode = '" + getQuestionMode + "' "
                                                + "and questionbatchnumber = '" + batchNumber + "' "
                                                + "and questionid = '" + logList[Integer.parseInt(
                                                randomCheckArrayList.get(pos).toString()) - 1] + "'").toString());
                                        //
                                        try{Thread.sleep(50);}catch(InterruptedException erer){}
                                        //
                                        answerOptionAArrayList.add(pos, 
                                            getOneDetail("select answeroptiona from questionprofile "
                                                + "where questionclass = '" + getTestClass + "' "
                                                + "and questionterm = '" + getTestTerm + "' "
                                                + "and questionsession = '" + getTestSession + "' "
                                                + "and questionsubject = '" + getTestSubject + "' "
                                                + "and questionmode = '" + getQuestionMode + "' "
                                                + "and questionbatchnumber = '" + batchNumber + "' "
                                                + "and questionid = '" + logList[Integer.parseInt(
                                                randomCheckArrayList.get(pos).toString()) - 1] + "'").toString());
                                        //
                                        try{Thread.sleep(50);}catch(InterruptedException erer){}
                                        //
                                        answerOptionBArrayList.add(pos, 
                                            getOneDetail("select answeroptionb from questionprofile "
                                                + "where questionclass = '" + getTestClass + "' "
                                                + "and questionterm = '" + getTestTerm + "' "
                                                + "and questionsession = '" + getTestSession + "' "
                                                + "and questionsubject = '" + getTestSubject + "' "
                                                + "and questionmode = '" + getQuestionMode + "' "
                                                + "and questionbatchnumber = '" + batchNumber + "' "
                                                + "and questionid = '" + logList[Integer.parseInt(
                                                randomCheckArrayList.get(pos).toString()) - 1] + "'").toString());
                                        //
                                        try{Thread.sleep(50);}catch(InterruptedException erer){}
                                        //
                                        answerOptionCArrayList.add(pos, 
                                            getOneDetail("select answeroptionc from questionprofile "
                                                + "where questionclass = '" + getTestClass + "' "
                                                + "and questionterm = '" + getTestTerm + "' "
                                                + "and questionsession = '" + getTestSession + "' "
                                                + "and questionsubject = '" + getTestSubject + "' "
                                                + "and questionmode = '" + getQuestionMode + "' "
                                                + "and questionbatchnumber = '" + batchNumber + "' "
                                                + "and questionid = '" + logList[Integer.parseInt(
                                                randomCheckArrayList.get(pos).toString()) - 1] + "'").toString());
                                        //
                                        try{Thread.sleep(50);}catch(InterruptedException erer){}
                                        //
                                        answerOptionDArrayList.add(pos, 
                                            getOneDetail("select answeroptiond from questionprofile "
                                                + "where questionclass = '" + getTestClass + "' "
                                                + "and questionterm = '" + getTestTerm + "' "
                                                + "and questionsession = '" + getTestSession + "' "
                                                + "and questionsubject = '" + getTestSubject + "' "
                                                + "and questionmode = '" + getQuestionMode + "' "
                                                + "and questionbatchnumber = '" + batchNumber + "' "
                                                + "and questionid = '" + logList[Integer.parseInt(
                                                randomCheckArrayList.get(pos).toString()) - 1] + "'").toString());
                                        //
                                        try{Thread.sleep(50);}catch(InterruptedException erer){}
                                        //
                                        answerOptionEArrayList.add(pos, 
                                            getOneDetail("select answeroptione from questionprofile "
                                                + "where questionclass = '" + getTestClass + "' "
                                                + "and questionterm = '" + getTestTerm + "' "
                                                + "and questionsession = '" + getTestSession + "' "
                                                + "and questionsubject = '" + getTestSubject + "' "
                                                + "and questionmode = '" + getQuestionMode + "' "
                                                + "and questionbatchnumber = '" + batchNumber + "' "
                                                + "and questionid = '" + logList[Integer.parseInt(
                                                randomCheckArrayList.get(pos).toString()) - 1] + "'").toString());
                                        //
                                        try{Thread.sleep(50);}catch(InterruptedException erer){}
                                        //
                                        correctOptionArrayList.add(pos, 
                                            getOneDetail("select correctoption from questionprofile "
                                                + "where questionclass = '" + getTestClass + "' "
                                                + "and questionterm = '" + getTestTerm + "' "
                                                + "and questionsession = '" + getTestSession + "' "
                                                + "and questionsubject = '" + getTestSubject + "' "
                                                + "and questionmode = '" + getQuestionMode + "' "
                                                + "and questionbatchnumber = '" + batchNumber + "' "
                                                + "and questionid = '" + logList[Integer.parseInt(
                                                randomCheckArrayList.get(pos).toString()) - 1] + "'").toString());
                                        //
                                        try{Thread.sleep(50);}catch(InterruptedException erer){}
                                        //
                                        pleaseWaitLab.setText("Loading Questions... Please Wait (" + displayTxt + "%)");
                                        //
                                        percentRater = "" + 
                                                (Double.parseDouble(percentRater) + (100 / trk)) + "000";
                                        percentRater = percentRater.substring(0, percentRater.indexOf(".") + 3);
                                        displayTxt = percentRater.substring(0, percentRater.indexOf("."));
                                    }
                                }
                            }
                            //
                            pleaseWaitLab.setText("Get Set!");
                            try{Thread.sleep(2000);}catch(InterruptedException erer){}
                            //
                            defaultInterface();
                        }
                    }
                    //
                    class jumpToQuestionPageControl extends MouseAdapter {
                        int pos = 0;
                        public jumpToQuestionPageControl(int p) {pos = p;}
                        public void mouseClicked(MouseEvent e) {
                            //
                            if(getOneDetail("select stoptest from stoptestprofile "
                                        + "where setupterm = '" + getTestTerm + "' "
                                        + "and setupclass = '" + getTestClass + "' "
                                        + "and setupsession = '" + getTestSession + "' "
                                        + "and setupsubject = '" + getTestSubject + "' "
                                        + "and questionmode = '" + getQuestionMode + "'").
                                    equalsIgnoreCase("yes")) {
                                //
                                promptLab.setText(
                                    getOneDetail("select stopmessage from stoptestprofile "
                                        + "where setupterm = '" + getTestTerm + "' "
                                        + "and setupclass = '" + getTestClass + "' "
                                        + "and setupsession = '" + getTestSession + "' "
                                        + "and setupsubject = '" + getTestSubject + "' "
                                        + "and questionmode = '" + getQuestionMode + "'").toUpperCase());
                                JOptionPane.showMessageDialog(null, 
                                        promptLab,
                                        getSchoolName.toUpperCase(),
                                        JOptionPane.ERROR_MESSAGE,
                                        errorIcon);
                                //
                                for(int pos = 0; pos < smartQuestionField.length; pos++) {
                                    smartQuestionField[pos].setEnabled(false);
                                }
                                //
                                nextButton.setEnabled(false);
                                prevButton.setEnabled(false);
                                saveTheoryButton.setEnabled(false);
                                submitButton.setEnabled(false);
                            }
                            else {
                                movementControl = (Integer.parseInt(
                                    smartQuestionField[pos].getText()) - 1);
                                //
                                if(movementControl == questionArrayList.size()) {
                                    movementControl--;
                                    nextButton.setEnabled(false);
                                    prevButton.setEnabled(true);
                                }
                                else if(movementControl < 0) {
                                    movementControl++;
                                    prevButton.setEnabled(false);
                                    nextButton.setEnabled(true);
                                }
                                else {
                                    prevButton.setEnabled(true);
                                    nextButton.setEnabled(true);
                                }   
                                //
                                loadEachQuestion();
                                loadAnswerToEachQuestion();
                            }
                        }
                    }
                    //
                    ArrayList randomPickerGenerator(int ct, int actualCt) {
                        //ct the total number of values to scabble from 
                        ArrayList randomCheckArrayList = new ArrayList();
                        //
                        for(int pos = 0; pos < actualCt; pos++) {
                            randomCheckArrayList.add(pos, new String(""));
                        }
                        //
                        int randomID = 0;
                        //
                        for(int pos = 0; pos < actualCt; pos++) {
                            //first turn
                            if (pos == 0) {
                                randomID = (int)(ct * Math.random() + 1);
                                //
                                randomCheckArrayList.set(pos, 
                                        new String("" + randomID));
                            }
                            else {
                                randomID = (int)(ct * Math.random() + 1);
                                //    
                                for(;;) {
                                    String valChk = "no";
                                    for(int chk = 0; chk < pos; chk++) {
                                        //
                                        if(!randomCheckArrayList.get(chk).toString().
                                                equalsIgnoreCase("" + randomID)) {
                                            //
                                            valChk = "no";
                                        }
                                        else {
                                            valChk = "yes";
                                            break;
                                        }
                                    }
                                    //
                                    if(valChk.equalsIgnoreCase("no")) {
                                        randomCheckArrayList.set(pos, 
                                                new String("" + randomID));
                                        //
                                        break;
                                    }
                                    else {
                                        randomID = (int)(ct * Math.random() + 1);
                                    }
                                }
                            }
                        }
                        //
                        return randomCheckArrayList;        
                    }
                    //
                    void defaultInterface() {
                        //
                        southPanel.removeAll();
                        optionSectionPanel.removeAll();
                        centQuestionSectionPanel.removeAll();
                        questionSectionPanel.removeAll();
                        centOptionSectionPanel.removeAll();
                        optionAPanel.removeAll();
                        optionBPanel.removeAll();
                        optionCPanel.removeAll();
                        optionDPanel.removeAll();
                        optionEPanel.removeAll();
                        formPicturePanel.removeAll();
                        centFormPanel.removeAll();
                        buttonPanel.removeAll();
                        centFormPicturePanel.removeAll();
                        centbuttonPanel.removeAll();
                        northPanel.removeAll();
                        testingInterfaceDisplayPanel.removeAll();
                        //
                        totalQuestionLab.setFont(new Font(getFontType, Font.BOLD, 14));
                        totalQuestionLab.setForeground(Color.BLUE);
                        totalAnsweredLab.setFont(new Font(getFontType, Font.BOLD, 14));
                        totalAnsweredLab.setForeground(Color.BLUE);
                        currentTestSubjectLab.setFont(new Font(getFontType, Font.BOLD, 14));
                        currentTestSubjectLab.setForeground(Color.BLUE);
                        studentRegistrationLab.setFont(new Font(getFontType, Font.BOLD, 14));
                        studentRegistrationLab.setForeground(Color.BLUE);
                        //
                        timeTextLab.setFont(new Font(getFontType, Font.BOLD, 10));
                        timeTextLab.setForeground(Color.BLUE);
                        //
                        timeField.setHorizontalAlignment(JTextField.CENTER);
                        timeField.setFont(new Font(getFontType, Font.BOLD, 30));
                        timeField.setForeground(Color.RED);
                        timeField.setBackground(Color.WHITE);
                        timeField.setBorder(BorderFactory.createEtchedBorder());
                        testDuration = 
                                getOneDetail("select testduration from loadtestprofile "
                                + "where setupclass = '" + getTestClass + "' "
                                + "and setupterm = '" + getTestTerm + "' "
                                + "and setupsession = '" + getTestSession + "' "
                                + "and questionmode = '" + getQuestionMode + "' "
                                + "and setupsubject = '" + getTestSubject + "'");
                        timeField.setText(testDuration + " : 00");
                        //
                        scorePerQuestion = 
                                getOneDetail("select scoreperquestion from loadtestprofile "
                                + "where setupclass = '" + getTestClass + "' "
                                + "and setupterm = '" + getTestTerm + "' "
                                + "and setupsession = '" + getTestSession + "' "
                                + "and questionmode = '" + getQuestionMode + "' "
                                + "and setupsubject = '" + getTestSubject + "'");
                        //
                        JPanel timeFieldPanel = new JPanel();
                        timeFieldPanel.setBackground(themeColor);
                        timeFieldPanel.setPreferredSize(new Dimension(50, 10));
                        timeFieldPanel.add(timeField);
                        //
                        JPanel centTimeFieldPanel = new JPanel();
                        centTimeFieldPanel.setBackground(themeColor);
                        centTimeFieldPanel.setLayout(new BorderLayout());
                        centTimeFieldPanel.add(timeFieldPanel, BorderLayout.CENTER);
                        centTimeFieldPanel.add(timeTextLab, BorderLayout.SOUTH);
                        //
                        JPanel studentPanel = new JPanel();
                        studentPanel.setBackground(themeColor);
                        studentPanel.setBorder(
                            BorderFactory.createTitledBorder(null,
                            " Student Statistics ",
                            TitledBorder.LEFT, 0, new Font(getFontType, Font.BOLD, 10), Color.RED));
                        studentPanel.setLayout(new GridLayout(2, 1, 0, 5));
                        studentPanel.add(currentTestSubjectLab);
                        studentPanel.add(studentRegistrationLab);
                        //
                        JPanel centStudentPanel = new JPanel();
                        centStudentPanel.setBackground(themeColor);
                        centStudentPanel.add(studentPanel);
                        //
                        JPanel statisticsPanel = new JPanel();
                        statisticsPanel.setBackground(themeColor);
                        statisticsPanel.setBorder(
                            BorderFactory.createTitledBorder(null,
                            " Test Statistics ",
                            TitledBorder.RIGHT, 0, new Font(getFontType, Font.BOLD, 10), Color.RED));
                        statisticsPanel.setLayout(new GridLayout(2, 1, 0, 5));
                        statisticsPanel.add(totalQuestionLab);
                        statisticsPanel.add(totalAnsweredLab);
                        //
                        JPanel centStatisticsPanel = new JPanel();
                        centStatisticsPanel.setBackground(themeColor);
                        centStatisticsPanel.add(statisticsPanel);
                        //
                        northPanel.setLayout(new GridLayout(1, 3, 0, 0));
                        northPanel.add(studentPanel);
                        northPanel.add(centTimeFieldPanel);
                        northPanel.add(statisticsPanel);
                        //
                        questionLabArea.setForeground(Color.BLACK);
                        questionLabArea.setFont(new Font("canbria", Font.BOLD, 20));
                        questionLabArea.setEditable(false);
                        questionLabArea.setLineWrap(true);
                        questionLabArea.setWrapStyleWord(true);
                        //
                        if(getQuestionMode.equalsIgnoreCase("theory")) {
                            theoryAnswerLabArea.setForeground(Color.BLACK);
                            theoryAnswerLabArea.setFont(new Font("canbria", Font.BOLD, 20));
                            theoryAnswerLabArea.setLineWrap(true);
                            theoryAnswerLabArea.setWrapStyleWord(true);
                            //
                            questionSectionPanel = new JPanel();
                            questionSectionPanel.setBackground(Color.WHITE);
                            questionSectionPanel.add(questionLabArea);
                            //
                            centQuestionSectionPanel.setBackground(Color.WHITE);
                            centQuestionSectionPanel.add(questionSectionPanel);
                            centQuestionSectionPanel.setBorder(
                                BorderFactory.createTitledBorder(null,
                                " Question " + (movementControl + 1) + " ",
                                TitledBorder.LEFT, 0, new Font(getFontType, 
                                    Font.BOLD, 10), Color.RED));
                            //
                            JPanel answerSectionPanel = new JPanel();
                            answerSectionPanel.setBackground(Color.WHITE);
                            answerSectionPanel.add(theoryAnswerLabArea);
                            //
                            centOptionSectionPanel.setBackground(Color.WHITE);
                            centOptionSectionPanel.add(answerSectionPanel);
                            centOptionSectionPanel.setBorder(
                                BorderFactory.createTitledBorder(null,
                                " Type Response To Question " + 
                                (movementControl + 1) + " Here ",
                                TitledBorder.LEFT, 0, new Font(getFontType, 
                                    Font.BOLD, 10), Color.RED));
                            //
                            formPicturePanel.setLayout(new GridLayout(2, 1, 0, 0));
                            formPicturePanel.add(new JScrollPane(centQuestionSectionPanel));
                            formPicturePanel.add(new JScrollPane(centOptionSectionPanel));
                        }
                        else if(getQuestionMode.equalsIgnoreCase("objective")) {
                            //
                            optionARadioButton.setBackground(Color.WHITE);
                            optionARadioButton.setForeground(Color.BLACK);
                            optionARadioButton.setFont(new Font(getFontType, Font.PLAIN, 14));
                            optionALab.setForeground(Color.BLACK);
                            optionALab.setFont(new Font(getFontType, Font.PLAIN, 14));
                            optionBRadioButton.setBackground(Color.WHITE);
                            optionBRadioButton.setForeground(Color.BLACK);
                            optionBRadioButton.setFont(new Font(getFontType, Font.PLAIN, 14));
                            optionBLab.setForeground(Color.BLACK);
                            optionBLab.setFont(new Font(getFontType, Font.PLAIN, 14));
                            optionCRadioButton.setBackground(Color.WHITE);
                            optionCRadioButton.setForeground(Color.BLACK);
                            optionCRadioButton.setFont(new Font(getFontType, Font.PLAIN, 14));
                            optionCLab.setForeground(Color.BLACK);
                            optionCLab.setFont(new Font(getFontType, Font.PLAIN, 14));
                            optionDRadioButton.setBackground(Color.WHITE);
                            optionDRadioButton.setForeground(Color.BLACK);
                            optionDRadioButton.setFont(new Font(getFontType, Font.PLAIN, 14));
                            optionDLab.setForeground(Color.BLACK);
                            optionDLab.setFont(new Font(getFontType, Font.PLAIN, 14));
                            optionERadioButton.setBackground(Color.WHITE);
                            optionERadioButton.setForeground(Color.BLACK);
                            optionERadioButton.setFont(new Font(getFontType, Font.PLAIN, 14));
                            optionELab.setForeground(Color.BLACK);
                            optionELab.setFont(new Font(getFontType, Font.PLAIN, 14));
                            //
                            optionAPanel.setBackground(Color.WHITE);
                            optionAPanel.add(optionARadioButton);
                            optionAPanel.add(optionALab);
                            optionBPanel.setBackground(Color.WHITE);
                            optionBPanel.add(optionBRadioButton);
                            optionBPanel.add(optionBLab);
                            optionCPanel.setBackground(Color.WHITE);
                            optionCPanel.add(optionCRadioButton);
                            optionCPanel.add(optionCLab);
                            optionDPanel.setBackground(Color.WHITE);
                            optionDPanel.add(optionDRadioButton);
                            optionDPanel.add(optionDLab);
                            optionEPanel.setBackground(Color.WHITE);
                            optionEPanel.add(optionERadioButton);
                            optionEPanel.add(optionELab);
                            //
                            JPanel leftOptionAPanel = new JPanel();
                            leftOptionAPanel.setLayout(new BorderLayout());
                            leftOptionAPanel.setBackground(Color.WHITE);
                            leftOptionAPanel.add(optionAPanel, BorderLayout.WEST);
                            JPanel leftOptionBPanel = new JPanel();
                            leftOptionBPanel.setLayout(new BorderLayout());
                            leftOptionBPanel.setBackground(Color.WHITE);
                            leftOptionBPanel.add(optionBPanel, BorderLayout.WEST);
                            JPanel leftOptionCPanel = new JPanel();
                            leftOptionCPanel.setLayout(new BorderLayout());
                            leftOptionCPanel.setBackground(Color.WHITE);
                            leftOptionCPanel.add(optionCPanel, BorderLayout.WEST);
                            JPanel leftOptionDPanel = new JPanel();
                            leftOptionDPanel.setLayout(new BorderLayout());
                            leftOptionDPanel.setBackground(Color.WHITE);
                            leftOptionDPanel.add(optionDPanel, BorderLayout.WEST);
                            JPanel leftOptionEPanel = new JPanel();
                            leftOptionEPanel.setLayout(new BorderLayout());
                            leftOptionEPanel.setBackground(Color.WHITE);
                            leftOptionEPanel.add(optionEPanel, BorderLayout.WEST);
                            //
                            optionSectionPanel.setBackground(Color.WHITE);
                            optionSectionPanel.setLayout(new GridLayout(5, 1, 0, 0));
                            optionSectionPanel.add(leftOptionAPanel);
                            optionSectionPanel.add(leftOptionBPanel);
                            optionSectionPanel.add(leftOptionCPanel);
                            optionSectionPanel.add(leftOptionDPanel);
                            optionSectionPanel.add(leftOptionEPanel);
                            //
                            questionSectionPanel.setBackground(Color.WHITE);
                            //
                            centQuestionSectionPanel.setBackground(Color.WHITE);
                            centQuestionSectionPanel.setLayout(new BorderLayout());
                            centQuestionSectionPanel.add(questionSectionPanel, BorderLayout.WEST);
                            centQuestionSectionPanel.setBorder(
                                BorderFactory.createTitledBorder(null,
                                " Question " + (movementControl + 1) + " ",
                                TitledBorder.LEFT, 0, new Font(getFontType, 
                                    Font.BOLD, 10), Color.RED));
                            //
                            JPanel answerSectionPanel = new JPanel();
                            answerSectionPanel.setBackground(Color.WHITE);
                            answerSectionPanel.add(optionSectionPanel);
                            //
                            centOptionSectionPanel.setBorder(
                                BorderFactory.createEtchedBorder());
                            centOptionSectionPanel.setBackground(Color.WHITE);
                            centOptionSectionPanel.setLayout(new BorderLayout());
                            centOptionSectionPanel.add(answerSectionPanel, BorderLayout.WEST);
                            //
                            formPicturePanel.setLayout(new GridLayout(2, 1, 0, 0));
                            formPicturePanel.add(new JScrollPane(centQuestionSectionPanel));
                            formPicturePanel.add(new JScrollPane(centOptionSectionPanel));
                        }
                        //
                        prevButton.setVerticalTextPosition(AbstractButton.CENTER);
                        prevButton.setHorizontalTextPosition(AbstractButton.RIGHT);
                        prevButton.setFont(new Font(getFontType, Font.BOLD, 10));
                        prevButton.setForeground(Color.RED);
                        prevButton.setBackground(Color.WHITE);
                        //
                        saveTheoryButton.setVerticalTextPosition(AbstractButton.CENTER);
                        saveTheoryButton.setHorizontalTextPosition(AbstractButton.CENTER);
                        saveTheoryButton.setFont(new Font(getFontType, Font.BOLD, 10));
                        saveTheoryButton.setForeground(Color.BLUE);
                        saveTheoryButton.setBackground(Color.WHITE);
                        //
                        nextButton.setVerticalTextPosition(AbstractButton.CENTER);
                        nextButton.setHorizontalTextPosition(AbstractButton.LEFT);
                        nextButton.setFont(new Font(getFontType, Font.BOLD, 10));
                        nextButton.setForeground(Color.RED);
                        nextButton.setBackground(Color.WHITE);
                        //
                        submitButton.setVerticalAlignment(AbstractButton.NORTH);
                        submitButton.setHorizontalTextPosition(AbstractButton.CENTER);
                        submitButton.setFont(new Font(getFontType, Font.BOLD, 10));
                        submitButton.setForeground(Color.RED);
                        submitButton.setBackground(Color.WHITE);
                        //
                        JPanel prevNextButtonPanel = new JPanel();
                        prevNextButtonPanel.setBackground(Color.WHITE);
                        if(getQuestionMode.equalsIgnoreCase("theory")) {
                            prevNextButtonPanel.add(new JLabel("                    "));
                            prevNextButtonPanel.add(prevButton);
                            prevNextButtonPanel.add(new JLabel("         "));
                            prevNextButtonPanel.add(saveTheoryButton);
                            prevNextButtonPanel.add(new JLabel("         "));
                            prevNextButtonPanel.add(nextButton);
                        }
                        else if(getQuestionMode.equalsIgnoreCase("objective")) {
                            prevNextButtonPanel.add(new JLabel("                    "));
                            prevNextButtonPanel.add(prevButton);
                            prevNextButtonPanel.add(new JLabel("    "));
                            prevNextButtonPanel.add(new JLabel(new ImageIcon("images/icon1.gif")));
                            prevNextButtonPanel.add(new JLabel(new ImageIcon("images/icon2.gif")));
                            prevNextButtonPanel.add(new JLabel(new ImageIcon("images/icon3.gif")));
                            prevNextButtonPanel.add(new JLabel("    "));
                            prevNextButtonPanel.add(nextButton);
                        }
                        //
                        JPanel stopButtonPanel = new JPanel();
                        stopButtonPanel.setBackground(Color.WHITE);
                        stopButtonPanel.add(submitButton);
                        stopButtonPanel.add(new JLabel("             "
                                + "                    "));
                        //
                        JPanel centStopButtonPanel = new JPanel();
                        centStopButtonPanel.setBackground(Color.WHITE);
                        centStopButtonPanel.add(stopButtonPanel);
                        //
                        buttonPanel.setBackground(Color.WHITE);
                        buttonPanel.setLayout(new BorderLayout());
                        buttonPanel.add(prevNextButtonPanel, BorderLayout.WEST);
                        buttonPanel.add(centStopButtonPanel, BorderLayout.EAST);
                        //
                        smartQuestionNumberPanel.setBorder(BorderFactory.createEtchedBorder());
                        smartQuestionNumberPanel.setBackground(Color.WHITE);
                        //
                        JPanel centSmartQuestionNumberPanel = new JPanel();
                        centSmartQuestionNumberPanel.setBackground(Color.WHITE);
                        centSmartQuestionNumberPanel.add(smartQuestionNumberPanel);
                        //
                        JPanel smartQuestionNumberButtonPanel = new JPanel();
                        smartQuestionNumberButtonPanel.setBackground(Color.WHITE);
                        smartQuestionNumberButtonPanel.setLayout(new BorderLayout());
                        smartQuestionNumberButtonPanel.add(centSmartQuestionNumberPanel, BorderLayout.NORTH);
                        smartQuestionNumberButtonPanel.add(buttonPanel, BorderLayout.SOUTH);
                        //
                        testingInterfaceDisplayPanel.setLayout(new BorderLayout());
                        testingInterfaceDisplayPanel.add(formPicturePanel, BorderLayout.CENTER);
                        testingInterfaceDisplayPanel.add(
                                smartQuestionNumberButtonPanel, BorderLayout.SOUTH);
                        validate();
                        //
                        timerControl = true;
                        new startTestTimer().start();
                        //
                        loadEachQuestion();
                        loadAnswerToEachQuestion();
                        //
                        prevButton.setEnabled(false);
                        //
                        nextButton.addActionListener(new getNextQuestionControl());
                        prevButton.addActionListener(new getPrevQuestionControl());
                        saveTheoryButton.addActionListener(new saveTheoryResponseControl());
                        //
                        submitButton.addActionListener(new submitTest());
                        //
                        optionARadioButton.addActionListener(new optionARadioControl());
                        optionBRadioButton.addActionListener(new optionBRadioControl());
                        optionCRadioButton.addActionListener(new optionCRadioControl());
                        optionDRadioButton.addActionListener(new optionDRadioControl());
                        optionERadioButton.addActionListener(new optionERadioControl());
                    }
                    //
                    class submitTest implements ActionListener {
                        
                        public void actionPerformed(ActionEvent e) {
                            promptLab.setText("Do You Really Want To Submit Test?");
                            int rep = JOptionPane.showConfirmDialog(null, 
                                    promptLab,
                                    getSchoolName.toUpperCase(),
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE,
                                    questionIcon);
                            //
                            if(rep == JOptionPane.YES_OPTION) {
                                timerControl = false;
                                //
                                endStudentTest();
                            }
                        }
                    }
                    //
                    JPanel previewSolutionPanel = new JPanel();
                    JLabel previewSolutionLab = new JLabel("Preview Solution Summary", 
                            JLabel.CENTER);
                    //
                    void endStudentTest() {
                        //
                        previewSolutionPanel.setBackground(Color.WHITE);
                        previewSolutionLab.setFont(new Font("canbria", Font.BOLD, 10));
                        previewSolutionLab.setForeground(Color.BLUE);
                        previewSolutionLab.setCursor(
                                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                        //
                        formPanel.removeAll();
                        formPicturePanel.removeAll();
                        centFormPanel.removeAll();
                        buttonPanel.removeAll();
                        centFormPicturePanel.removeAll();
                        centbuttonPanel.removeAll();
                        testingInterfaceDisplayPanel.removeAll();
                        northPanel.removeAll();
                        southPanel.removeAll();
                        //
                        studentRegistrationLab.setText(
                                studentRegistrationLab.getText().substring(
                                studentRegistrationLab.getText().indexOf(":") + 2));
                        currentTestSubjectLab.setText(
                                currentTestSubjectLab.getText().substring(
                                currentTestSubjectLab.getText().indexOf(":") + 2));
                        //
                        totalAnsweredLab.setHorizontalAlignment(SwingConstants.LEFT);
                        totalAnsweredLab.setFont(new Font("canbria", Font.PLAIN, 16));
                        totalQuestionLab.setHorizontalAlignment(SwingConstants.LEFT);
                        totalQuestionLab.setFont(new Font("canbria", Font.PLAIN, 16));
                        studentRegistrationLab.setFont(new Font("canbria", Font.PLAIN, 16));
                        currentTestSubjectLab.setFont(new Font("canbria", Font.PLAIN, 16));
                        //
                        returnButton.setFont(new Font("canbria", Font.PLAIN, 10));
                        returnButton.setBackground(Color.WHITE);
                        returnButton.setForeground(Color.RED);
                        returnButton.setEnabled(false);
                        //
                        timeField.setHorizontalAlignment(JTextField.LEFT);
                        timeField.setFont(new Font("canbria", Font.PLAIN, 16));
                        timeField.setBackground(Color.WHITE);
                        timeField.setBorder(BorderFactory.createEmptyBorder());
                        timeField.setForeground(Color.BLUE);
                        //
                        JPanel endTestPanel = new JPanel();
                        endTestPanel.setBackground(Color.WHITE);
                        endTestPanel.setLayout(new GridLayout(11, 2, 0, 0)); 
                        endTestPanel.setBorder(
                            BorderFactory.createTitledBorder(null,
                            " Test Summary ",
                            TitledBorder.LEFT, 0, new Font(getFontType, 
                                Font.BOLD, 10), Color.BLUE));
                        //
                        questionParaLab[0].setText("Registration Number: ");
                        endTestPanel.add(questionParaLab[0]);
                        endTestPanel.add(studentRegistrationLab);
                        questionParaLab[1].setText("Test Subject: ");
                        endTestPanel.add(questionParaLab[1]);
                        endTestPanel.add(currentTestSubjectLab);
                        questionParaLab[2].setText("Total Questions: ");
                        endTestPanel.add(questionParaLab[2]);
                        endTestPanel.add(totalQuestionLab);
                        questionParaLab[3].setText("Questions Answered: ");
                        endTestPanel.add(questionParaLab[3]);
                        endTestPanel.add(totalAnsweredLab);
                        //
                        int aCount = 0;
                        //
                        String batchNum = 
                                getOneDetail("select questionbatchnumber from loadtestprofile "
                                + "where setupclass = '" + getTestClass + "' "
                                + "and setupterm = '" + getTestTerm + "' "
                                + "and setupsession = '" + getTestSession + "' "
                                + "and questionmode = '" + getQuestionMode + "' "
                                + "and setupsubject = '" + getTestSubject + "'");
                        //
                        if(getQuestionMode.equalsIgnoreCase("objective")) {
                            //
                            for(int pos = 0; pos < questionArrayList.size(); pos++) {
                                if(getOneDetail("select correctoption from questionprofile "
                                        + "where questionclass = '" + getTestClass + "' "
                                        + "and questionterm = '" + getTestTerm + "' "
                                        + "and questionsession = '" + getTestSession + "' "
                                        + "and questionsubject = '" + getTestSubject + "' "
                                        + "and questionmode = '" + getQuestionMode + "' "
                                        + "and questionbody = '" + questionArrayList.get(pos).toString() + "' "
                                        + "and questionbatchnumber = '" + batchNum + "'").
                                    equalsIgnoreCase(answerCollectorArrayList.get(pos).toString())) {
                                    //
                                    aCount++;
                                }
                            }
                        }
                        //
                        JLabel totalCorrectLab = new JLabel("Correct Answered: ", JLabel.RIGHT);
                        totalCorrectLab.setFont(new Font("canbria", Font.BOLD, 14));
                        totalCorrectLab.setForeground(Color.RED);
                        //
                        JLabel totalCorrectAnsweredLab = new JLabel(
                                getQuestionMode.equalsIgnoreCase("objective") ? "" + aCount : 
                                "Not Evaluated");
                        totalCorrectAnsweredLab.setFont(new Font("canbria", Font.PLAIN, 16));
                        totalCorrectAnsweredLab.setForeground(Color.BLUE);
                        
                        endTestPanel.add(totalCorrectLab);
                        endTestPanel.add(totalCorrectAnsweredLab);
                        //
                        JLabel scorePercentLab = new JLabel("" + 
                                (Double.parseDouble(scorePerQuestion) * aCount));
                        //
                        String pCent = ((aCount * 100) / 
                                Double.parseDouble(totalQuestionLab.getText().substring(0, 
                                totalQuestionLab.getText().indexOf(" ")))) + "000";
                        //
                        pCent = pCent.substring(0, pCent.indexOf(".") + 3);
                        scorePerQuestion = "" + 
                                Integer.parseInt(scorePercentLab.getText()) + "/" + pCent + "%";
                        //
                        scorePercentLab.setFont(new Font("canbria", Font.PLAIN, 16));
                        scorePercentLab.setForeground(Color.BLUE);
                        scorePercentLab.setText(
                                getQuestionMode.equalsIgnoreCase("objective") ? 
                                scorePerQuestion : "Not Evaluated");
                        
                        questionParaLab[4].setText("Total Scores: ");
                        endTestPanel.add(questionParaLab[4]);
                        //
                        JLabel disabledText = scorePercentLab;
                        //
                        String getCtrl = getOneDetail("select displaytestscore "
                                + "from loadtestprofile "
                                + "where setupclass = '" + getTestClass + "' "
                                + "and setupterm = '" + getTestTerm + "' "
                                + "and setupsession = '" + getTestSession + "' "
                                + "and questionmode = '" + getQuestionMode + "' "
                                + "and setupsubject = '" + getTestSubject + "'");
                        //
                        if(getCtrl.equalsIgnoreCase("yes")) {
                            endTestPanel.add(scorePercentLab);
                        }
                        else {
                            disabledText.setText("Scores' Display Disabled");
                            endTestPanel.add(disabledText);
                        }
                        //
                        int timer = (Integer.parseInt(testDuration) - 
                                (Integer.parseInt(timeField.getText().substring(0, 
                                timeField.getText().indexOf(" ")))));
                        //
                        JLabel timerLab = new JLabel("" + timer + " Minute(s)");
                        timerLab.setFont(new Font("canbria", Font.PLAIN, 16));
                        timerLab.setForeground(Color.BLUE);
                        //
                        questionParaLab[5].setText("Total Time Spent: ");
                        endTestPanel.add(questionParaLab[5]);
                        endTestPanel.add(timerLab);
                        //
                        endTestPanel.add(new JLabel(""));
                        endTestPanel.add(new JLabel(""));
                        endTestPanel.add(new JLabel(""));
                        endTestPanel.add(new JLabel(""));
                        endTestPanel.add(new JLabel(""));
                        endTestPanel.add(new JLabel(""));
                        if(getOneDetail("select enabledisable from studentexperience").
                                equalsIgnoreCase("true") && 
                            getQuestionMode.equalsIgnoreCase("objective")) {
                            //
                            endTestPanel.add(previewSolutionLab);
                        }
                        else {
                            endTestPanel.add(new JLabel(""));
                        }
                        endTestPanel.add(returnButton);
                        //
                        goByeLab.setHorizontalAlignment(JTextField.CENTER);
                        goByeLab.setFont(new Font(getFontType, Font.PLAIN, 30));
                        goByeLab.setForeground(Color.BLUE);
                        goByeLab.setBackground(Color.WHITE);
                        goByeLab.setBorder(BorderFactory.createEtchedBorder());
                        //
                        JPanel centEndTestPanel = new JPanel();
                        centEndTestPanel.setBackground(Color.WHITE);
                        centEndTestPanel.add(endTestPanel);
                        //
                        JPanel endTestByePanel = new JPanel();
                        endTestByePanel.setBorder(BorderFactory.createEtchedBorder());
                        endTestByePanel.setBackground(Color.WHITE);
                        endTestByePanel.setLayout(new BorderLayout());
                        endTestByePanel.add(centEndTestPanel, BorderLayout.NORTH);
                        endTestByePanel.add(new JScrollPane(previewSolutionPanel), 
                                BorderLayout.CENTER);
                        endTestByePanel.add(goByeLab, BorderLayout.SOUTH);
                        //
                        testingInterfaceDisplayPanel.setLayout(new BorderLayout());
                        testingInterfaceDisplayPanel.add(endTestByePanel, BorderLayout.CENTER);
                        validate();
                        //display end test interface
                        generateStudentReport();
                        //
                        new blinkByeText().start();
                        returnButton.addActionListener(new returnToLogin());
                        previewSolutionLab.addMouseListener(new displaySolution());
                    }
                    //
                    class displaySolution extends MouseAdapter {
                        
                        public void mouseClicked(MouseEvent e) {
                            int row = questionArrayList.size();
                            //
                            previewSolutionPanel.removeAll();
                            validate();
                            //
                            previewSolutionPanel.setLayout(
                                    new GridLayout(row, 1, 0, 2));
                            //
                            JPanel[] qPanel = new JPanel[row],
                                    solutionPanel = new JPanel[row];
                            //
                            JTextArea[] mainQuestionArea = new JTextArea[row], 
                                        explainArea = new JTextArea[row];
                            //
                            for(int pos = 0; pos < row; pos++) {
                                JPanel qSPanel = new JPanel();
                                qSPanel.setLayout(new BorderLayout());
                                //
                                qPanel[pos] = new JPanel();
                                qPanel[pos].setBackground(Color.WHITE);
                                qPanel[pos].setBorder(
                                    BorderFactory.createTitledBorder(null,
                                    " Question Revisiting ",
                                    TitledBorder.LEFT, 0, new Font(getFontType, 
                                        Font.BOLD, 10), Color.RED));
                                //
                                solutionPanel[pos] = new JPanel();
                                solutionPanel[pos].setBackground(Color.WHITE);
                                solutionPanel[pos].setBorder(
                                    BorderFactory.createTitledBorder(null,
                                    " Question Solution/Explanation ",
                                    TitledBorder.LEFT, 0, new Font(getFontType, 
                                        Font.BOLD, 10), Color.RED));
                                //
                                mainQuestionArea[pos] = new JTextArea(15, 60); 
                                explainArea[pos] = new JTextArea(15, 60); 
                                //
                                mainQuestionArea[pos].setForeground(Color.BLACK);
                                mainQuestionArea[pos].setFont(new Font("canbria", Font.BOLD, 14));
                                mainQuestionArea[pos].setEditable(false);
                                mainQuestionArea[pos].setLineWrap(true);
                                mainQuestionArea[pos].setWrapStyleWord(true);
                                //
                                explainArea[pos].setForeground(Color.BLACK);
                                explainArea[pos].setFont(new Font("canbria", Font.BOLD, 14));
                                explainArea[pos].setEditable(false);
                                explainArea[pos].setLineWrap(true);
                                explainArea[pos].setWrapStyleWord(true);
                                //
                                String qText = questionArrayList.get(pos).toString();
                                //
                                if(qText.startsWith("//" + getServerIP + "/")) {
                                    //
                                    qPanel[pos].add(new JLabel(new ImageIcon(qText)));
                                    solutionPanel[pos].add(new JLabel(
                                            new ImageIcon(
                                            getOneDetail("select explanation "
                                            + "from questionexplanation "
                                            + "where questionbody = '" + qText + "'"))));
                                    //
                                    validate();
                                }
                                else {
                                    mainQuestionArea[pos].setText(qText);
                                    explainArea[pos].setText(
                                            getOneDetail("select explanation "
                                            + "from questionexplanation "
                                            + "where questionbody = '" + qText + "'"));
                                    //
                                    qPanel[pos].add(mainQuestionArea[pos]);
                                    solutionPanel[pos].add(explainArea[pos]);
                                    validate();
                                }
                                //
                                qSPanel.add(qPanel[pos], BorderLayout.NORTH);
                                qSPanel.add(solutionPanel[pos], BorderLayout.SOUTH);
                                qSPanel.setBackground(Color.WHITE);
                                //
                                JPanel centQSPanel = new JPanel();
                                centQSPanel.add(qSPanel);
                                //
                                previewSolutionPanel.add(centQSPanel);
                                validate();
                            }
                        }
                    }
                    //
                    class returnToLogin implements ActionListener {
                        
                        public void actionPerformed(ActionEvent e) {
                            //
                            formPanel.removeAll();
                            formPicturePanel.removeAll();
                            centFormPanel.removeAll();
                            buttonPanel.removeAll();
                            centFormPicturePanel.removeAll();
                            centbuttonPanel.removeAll();
                            northPanel.removeAll();
                            testingInterfaceDisplayPanel.removeAll();
                            southPanel.removeAll();
                            validate();
                            //
                            new newStudentTestControl();
                        }
                    }
                    //
                    class blinkByeText extends Thread {
                        public void run() {
                            int count = 0;
                            //
                            for(;;) {
                                goByeLab.setForeground(Color.RED);
                                try{sleep(1000);}catch(InterruptedException erer){}
                                goByeLab.setForeground(Color.WHITE);
                                try{sleep(1000);}catch(InterruptedException erer){}
                                //
                                count++;
                                //
                                if(count == 5) {
                                    returnButton.setEnabled(true);
                                }
                            }
                        }
                    }
                    //
                    void generateStudentReport() {
                        //check the number of currect option chosen
                        if(getQuestionMode.equalsIgnoreCase("objective")) {
                            answeredQuestionCounter = 0;
                            for(int pos = 0; pos < answerCollectorArrayList.size(); pos++) {
                                if(answerCollectorArrayList.get(pos).toString().
                                    equalsIgnoreCase(
                                    correctOptionArrayList.get(pos).toString())) {
                                    //
                                    answeredQuestionCounter++;
                                }
                            }
                        }
                        //
                        new loadScoreForStudent().start();
                    }
                    //
                    class loadScoreForStudent extends Thread {
                        
                        public void run() {
                            try {sleep(100);}catch(InterruptedException erer){}
                            //
                            if(getQuestionMode.equalsIgnoreCase("objective")) {
                                insertUpdateDelete("update testscoresprofile "
                                    + "set questionanswered = '" + 
                                                totalAnsweredLab.getText().substring(0, 
                                                totalAnsweredLab.getText().indexOf(" ")) + "', "
                                        + "testscore = '" + scorePerQuestion + "' "
                                        + "where registrationnumber = '" + 
                                                registrationNumberField.getText().toLowerCase() + "' "
                                        + "and testsubject = '" + getTestSubject + "' "
                                        + "and testclass = '" + getTestClass + "' "
                                        + "and testterm = '" + getTestTerm + "' "
                                        + "and questionmode = '" + getQuestionMode + "' "
                                        + "and testsession = '" + getTestSession + "'");
                            }
                            else if(getQuestionMode.equalsIgnoreCase("theory")) {
                                //
                                insertUpdateDelete("update testscoresprofile "
                                       + "set questionanswered = '" + 
                                       totalAnsweredLab.getText().substring(0, 
                                         totalAnsweredLab.getText().indexOf(" ")) + "' "
                                          + "where registrationnumber = '" + 
                                      registrationNumberField.getText().toLowerCase() + "' "
                                          + "and testsubject = '" + getTestSubject + "' "
                                          + "and testclass = '" + getTestClass + "' "
                                          + "and testterm = '" + getTestTerm + "' "
                                          + "and testsession = '" + getTestSession + "' "
                                          + "and questionmode = '" + getQuestionMode + "' "
                                          + "and datewritten = '" + (day + " " + 
                                          letterMonth(Integer.parseInt(mon)).toLowerCase() + 
                                          " 20" + year) + "'");
                            }
                            //
                            getQuestionMode = "";
                            getTestClass = "";
                            getTestTerm = "";
                            getTestSession = "";
                            getTestSubject = "";
                        }
                    }
                    //
                    class optionARadioControl implements ActionListener {
                        public void actionPerformed(ActionEvent e) {
                            optionBRadioButton.setSelected(false);
                            optionBRadioButton.setFont(new Font(getFontType, Font.PLAIN, 14));
                            optionBLab.setFont(new Font(getFontType, Font.PLAIN, 14));
                            optionCRadioButton.setFont(new Font(getFontType, Font.PLAIN, 14));
                            optionCRadioButton.setSelected(false);
                            optionCLab.setFont(new Font(getFontType, Font.PLAIN, 14));
                            optionDRadioButton.setFont(new Font(getFontType, Font.PLAIN, 14));
                            optionDRadioButton.setSelected(false);
                            optionDLab.setFont(new Font(getFontType, Font.PLAIN, 14));
                            optionERadioButton.setFont(new Font(getFontType, Font.PLAIN, 14));
                            optionERadioButton.setSelected(false);
                            optionELab.setFont(new Font(getFontType, Font.PLAIN, 14));
                            //
                            optionALab.setFont(new Font(getFontType, Font.BOLD, 14));
                            optionARadioButton.setFont(new Font(getFontType, Font.BOLD, 14));
                            //SET CORRECT TRACKER
                            answerCollectorArrayList.set(movementControl, 
                                        new String("a"));
                            //
                            for(int pos = 0; pos < smartQuestionField.length; pos++) {
                                if((Integer.parseInt(smartQuestionField[pos].getText()) - 1) 
                                        == movementControl) {
                                    smartQuestionField[pos].setBackground(Color.CYAN);
                                    break;
                                }
                            }
                            //
                            answeredQuestionCounter = 0;
                            for(int pos = 0; pos < answerCollectorArrayList.size(); pos++) {
                                if(!answerCollectorArrayList.get(pos).toString().
                                    equalsIgnoreCase("")) {
                                    //
                                    answeredQuestionCounter++;
                                }
                            }
                            //
                            totalAnsweredLab.setText(answeredQuestionCounter + 
                                    " Question(s) Answered  ");
                        }
                    }
                    //
                    class optionBRadioControl implements ActionListener {
                        public void actionPerformed(ActionEvent e) {
                            optionARadioButton.setSelected(false);
                            optionARadioButton.setFont(new Font(getFontType, Font.PLAIN, 14));
                            optionALab.setFont(new Font(getFontType, Font.PLAIN, 14));
                            optionCRadioButton.setSelected(false);
                            optionCRadioButton.setFont(new Font(getFontType, Font.PLAIN, 14));
                            optionCLab.setFont(new Font(getFontType, Font.PLAIN, 14));
                            optionDRadioButton.setSelected(false);
                            optionDRadioButton.setFont(new Font(getFontType, Font.PLAIN, 14));
                            optionDLab.setFont(new Font(getFontType, Font.PLAIN, 14));
                            optionERadioButton.setSelected(false);
                            optionERadioButton.setFont(new Font(getFontType, Font.PLAIN, 14));
                            optionELab.setFont(new Font(getFontType, Font.PLAIN, 14));
                            //
                            optionBLab.setFont(new Font(getFontType, Font.BOLD, 14));
                            optionBRadioButton.setFont(new Font(getFontType, Font.BOLD, 14));
                            //SET CORRECT TRACKER
                            answerCollectorArrayList.set(movementControl, 
                                        new String("b"));
                            //
                            for(int pos = 0; pos < smartQuestionField.length; pos++) {
                                if((Integer.parseInt(smartQuestionField[pos].getText()) - 1) 
                                        == movementControl) {
                                    smartQuestionField[pos].setBackground(Color.CYAN);
                                    break;
                                }
                            }
                            //
                            answeredQuestionCounter = 0;
                            for(int pos = 0; pos < answerCollectorArrayList.size(); pos++) {
                                if(!answerCollectorArrayList.get(pos).toString().
                                    equalsIgnoreCase("")) {
                                    //
                                    answeredQuestionCounter++;
                                }
                            }
                            //
                            totalAnsweredLab.setText(answeredQuestionCounter + 
                                    " Question(s) Answered  ");
                        }
                    }
                    //
                    class optionCRadioControl implements ActionListener {
                        public void actionPerformed(ActionEvent e) {
                            optionBRadioButton.setSelected(false);
                            optionBRadioButton.setFont(new Font(getFontType, Font.PLAIN, 14));
                            optionBLab.setFont(new Font(getFontType, Font.PLAIN, 14));
                            optionARadioButton.setSelected(false);
                            optionARadioButton.setFont(new Font(getFontType, Font.PLAIN, 14));
                            optionALab.setFont(new Font(getFontType, Font.PLAIN, 14));
                            optionDRadioButton.setSelected(false);
                            optionDRadioButton.setFont(new Font(getFontType, Font.PLAIN, 14));
                            optionDLab.setFont(new Font(getFontType, Font.PLAIN, 14));
                            optionERadioButton.setSelected(false);
                            optionERadioButton.setFont(new Font(getFontType, Font.PLAIN, 14));
                            optionELab.setFont(new Font(getFontType, Font.PLAIN, 14));
                            //
                            optionCLab.setFont(new Font(getFontType, Font.BOLD, 14));
                            optionCRadioButton.setFont(new Font(getFontType, Font.BOLD, 14));
                            //SET CORRECT TRACKER
                            answerCollectorArrayList.set(movementControl, 
                                        new String("c"));
                            //
                            for(int pos = 0; pos < smartQuestionField.length; pos++) {
                                if((Integer.parseInt(smartQuestionField[pos].getText()) - 1) 
                                        == movementControl) {
                                    smartQuestionField[pos].setBackground(Color.CYAN);
                                    break;
                                }
                            }
                            //
                            answeredQuestionCounter = 0;
                            for(int pos = 0; pos < answerCollectorArrayList.size(); pos++) {
                                if(!answerCollectorArrayList.get(pos).toString().
                                    equalsIgnoreCase("")) {
                                    //
                                    answeredQuestionCounter++;
                                }
                            }
                            //
                            totalAnsweredLab.setText(answeredQuestionCounter + 
                                    " Question(s) Answered  ");
                        }
                    }
                    //
                    class optionDRadioControl implements ActionListener {
                        public void actionPerformed(ActionEvent e) {
                            optionBRadioButton.setSelected(false);
                            optionBRadioButton.setFont(new Font(getFontType, Font.PLAIN, 14));
                            optionBLab.setFont(new Font(getFontType, Font.PLAIN, 14));
                            optionCRadioButton.setSelected(false);
                            optionCRadioButton.setFont(new Font(getFontType, Font.PLAIN, 14));
                            optionCLab.setFont(new Font(getFontType, Font.PLAIN, 14));
                            optionARadioButton.setSelected(false);
                            optionARadioButton.setFont(new Font(getFontType, Font.PLAIN, 14));
                            optionALab.setFont(new Font(getFontType, Font.PLAIN, 14));
                            optionERadioButton.setSelected(false);
                            optionERadioButton.setFont(new Font(getFontType, Font.PLAIN, 14));
                            optionELab.setFont(new Font(getFontType, Font.PLAIN, 14));
                            //
                            optionDLab.setFont(new Font(getFontType, Font.BOLD, 14));
                            optionDRadioButton.setFont(new Font(getFontType, Font.BOLD, 14));
                            //SET CORRECT TRACKER
                            answerCollectorArrayList.set(movementControl, 
                                        new String("d"));
                            //
                            for(int pos = 0; pos < smartQuestionField.length; pos++) {
                                if((Integer.parseInt(smartQuestionField[pos].getText()) - 1) 
                                        == movementControl) {
                                    smartQuestionField[pos].setBackground(Color.CYAN);
                                    break;
                                }
                            }
                            //
                            answeredQuestionCounter = 0;
                            for(int pos = 0; pos < answerCollectorArrayList.size(); pos++) {
                                if(!answerCollectorArrayList.get(pos).toString().
                                    equalsIgnoreCase("")) {
                                    //
                                    answeredQuestionCounter++;
                                }
                            }
                            //
                            totalAnsweredLab.setText(answeredQuestionCounter + 
                                    " Question(s) Answered  ");
                        }
                    }
                    //
                    class optionERadioControl implements ActionListener {
                        public void actionPerformed(ActionEvent e) {
                            optionBRadioButton.setSelected(false);
                            optionBRadioButton.setFont(new Font(getFontType, Font.PLAIN, 14));
                            optionBLab.setFont(new Font(getFontType, Font.PLAIN, 14));
                            optionCRadioButton.setSelected(false);
                            optionCRadioButton.setFont(new Font(getFontType, Font.PLAIN, 14));
                            optionCLab.setFont(new Font(getFontType, Font.PLAIN, 14));
                            optionDRadioButton.setSelected(false);
                            optionDRadioButton.setFont(new Font(getFontType, Font.PLAIN, 14));
                            optionDLab.setFont(new Font(getFontType, Font.PLAIN, 14));
                            optionARadioButton.setSelected(false);
                            optionARadioButton.setFont(new Font(getFontType, Font.PLAIN, 14));
                            optionALab.setFont(new Font(getFontType, Font.PLAIN, 14));
                            //
                            optionELab.setFont(new Font(getFontType, Font.BOLD, 14));
                            optionERadioButton.setFont(new Font(getFontType, Font.BOLD, 14));
                            //SET CORRECT TRACKER
                            answerCollectorArrayList.set(movementControl, 
                                        new String("e"));
                            //
                            for(int pos = 0; pos < smartQuestionField.length; pos++) {
                                if((Integer.parseInt(smartQuestionField[pos].getText()) - 1) 
                                        == movementControl) {
                                    smartQuestionField[pos].setBackground(Color.CYAN);
                                    break;
                                }
                            }
                            //
                            answeredQuestionCounter = 0;
                            for(int pos = 0; pos < answerCollectorArrayList.size(); pos++) {
                                if(!answerCollectorArrayList.get(pos).toString().
                                    equalsIgnoreCase("")) {
                                    //
                                    answeredQuestionCounter++;
                                }
                            }
                            //
                            totalAnsweredLab.setText(answeredQuestionCounter + 
                                    " Question(s) Answered  ");
                        }
                    }
                    //
                    class getNextQuestionControl implements ActionListener {
                        
                        public void actionPerformed(ActionEvent e) {
                            //
                            if(getOneDetail("select stoptest from stoptestprofile "
                                        + "where setupterm = '" + getTestTerm + "' "
                                        + "and setupclass = '" + getTestClass + "' "
                                        + "and setupsession = '" + getTestSession + "' "
                                        + "and setupsubject = '" + getTestSubject + "' "
                                        + "and questionmode = '" + getQuestionMode + "'").
                                    equalsIgnoreCase("yes")) {
                                //
                                promptLab.setText(
                                    getOneDetail("select stopmessage from stoptestprofile "
                                        + "where setupterm = '" + getTestTerm + "' "
                                        + "and setupclass = '" + getTestClass + "' "
                                        + "and setupsession = '" + getTestSession + "' "
                                        + "and setupsubject = '" + getTestSubject + "' "
                                        + "and questionmode = '" + getQuestionMode + "'").toUpperCase());
                                JOptionPane.showMessageDialog(null, 
                                        promptLab,
                                        getSchoolName.toUpperCase(),
                                        JOptionPane.ERROR_MESSAGE,
                                        errorIcon);
                                //
                                for(int pos = 0; pos < smartQuestionField.length; pos++) {
                                    smartQuestionField[pos].setEnabled(false);
                                }
                                //
                                nextButton.setEnabled(false);
                                prevButton.setEnabled(false);
                                saveTheoryButton.setEnabled(false);
                                submitButton.setEnabled(false);
                            }
                            else {
                                //
                                movementControl++;
                                //
                                if(movementControl == questionArrayList.size()) {
                                    movementControl--;
                                    //
                                    nextButton.setEnabled(false);
                                    prevButton.setEnabled(true);
                                    saveTheoryButton.setEnabled(false);
                                }
                                else {
                                    prevButton.setEnabled(true);
                                    nextButton.setEnabled(true);
                                    saveTheoryButton.setEnabled(true);
                                    //
                                    loadEachQuestion();
                                    loadAnswerToEachQuestion();
                                }
                            }
                        }
                    }
                    //
                    class getPrevQuestionControl implements ActionListener {
                        
                        public void actionPerformed(ActionEvent e) {
                            //
                            if(getOneDetail("select stoptest from stoptestprofile "
                                        + "where setupterm = '" + getTestTerm + "' "
                                        + "and setupclass = '" + getTestClass + "' "
                                        + "and setupsession = '" + getTestSession + "' "
                                        + "and setupsubject = '" + getTestSubject + "' "
                                        + "and questionmode = '" + getQuestionMode + "'").
                                    equalsIgnoreCase("yes")) {
                                //
                                promptLab.setText(
                                    getOneDetail("select stopmessage from stoptestprofile "
                                        + "where setupterm = '" + getTestTerm + "' "
                                        + "and setupclass = '" + getTestClass + "' "
                                        + "and setupsession = '" + getTestSession + "' "
                                        + "and setupsubject = '" + getTestSubject + "' "
                                        + "and questionmode = '" + getQuestionMode + "'").toUpperCase());
                                JOptionPane.showMessageDialog(null, 
                                        promptLab,
                                        getSchoolName.toUpperCase(),
                                        JOptionPane.ERROR_MESSAGE,
                                        errorIcon);
                                //
                                for(int pos = 0; pos < smartQuestionField.length; pos++) {
                                    smartQuestionField[pos].setEnabled(false);
                                }
                                //
                                nextButton.setEnabled(false);
                                prevButton.setEnabled(false);
                                saveTheoryButton.setEnabled(false);
                                submitButton.setEnabled(false);
                            }
                            else {
                                //
                                movementControl--;
                                //
                                if(movementControl < 0) {
                                    movementControl++;
                                    //
                                    prevButton.setEnabled(false);
                                    nextButton.setEnabled(true);
                                    saveTheoryButton.setEnabled(false);
                                }
                                else {
                                    nextButton.setEnabled(true);
                                    prevButton.setEnabled(true);
                                    saveTheoryButton.setEnabled(true);
                                    //
                                    loadEachQuestion();
                                    loadAnswerToEachQuestion();
                                }
                            }
                        }
                    }
                    //
                    class saveTheoryResponseControl implements ActionListener {
                        
                        public void actionPerformed(ActionEvent e) {
                            //
                            if(getOneDetail("select stoptest from stoptestprofile "
                                        + "where setupterm = '" + getTestTerm + "' "
                                        + "and setupclass = '" + getTestClass + "' "
                                        + "and setupsession = '" + getTestSession + "' "
                                        + "and setupsubject = '" + getTestSubject + "' "
                                        + "and questionmode = '" + getQuestionMode + "'").
                                    equalsIgnoreCase("yes")) {
                                //
                                promptLab.setText(
                                    getOneDetail("select stopmessage from stoptestprofile "
                                        + "where setupterm = '" + getTestTerm + "' "
                                        + "and setupclass = '" + getTestClass + "' "
                                        + "and setupsession = '" + getTestSession + "' "
                                        + "and setupsubject = '" + getTestSubject + "' "
                                        + "and questionmode = '" + getQuestionMode + "'").toUpperCase());
                                JOptionPane.showMessageDialog(null, 
                                        promptLab,
                                        getSchoolName.toUpperCase(),
                                        JOptionPane.ERROR_MESSAGE,
                                        errorIcon);
                                //
                                for(int pos = 0; pos < smartQuestionField.length; pos++) {
                                    smartQuestionField[pos].setEnabled(false);
                                }
                                //
                                nextButton.setEnabled(false);
                                prevButton.setEnabled(false);
                                saveTheoryButton.setEnabled(false);
                                submitButton.setEnabled(false);
                            }
                            else {
                                //
                                for(int pos = 0; pos <= questionArrayList.size(); pos++) {
                                    if(pos == movementControl) {
                                        //check if question exist
                                        if(getNumberOfDetails("select * from studenttheoryanswerprofile "
                                                + "where registrationnumber = '" + 
                                                registrationNumberField.getText().toLowerCase() + "' "
                                                + "and testsubject = '" + getTestSubject + "' "
                                                + "and testclass = '" + getTestClass + "' "
                                                + "and testterm = '" + getTestTerm + "' "
                                                + "and testsession = '" + getTestSession + "' "
                                                + "and question = '" + questionArrayList.get(pos).toString() + "' "
                                                + "and questionmode = '" + getQuestionMode + "' "
                                                + "and datewritten = '" + (day + " " + 
                                                letterMonth(Integer.parseInt(mon)).toLowerCase() + 
                                                " 20" + year) + "'") > 0) {
                                             //
                                             answerCollectorArrayList.set(pos, 
                                                     theoryAnswerLabArea.getText().toString());
                                             //
                                             insertUpdateDelete("update studenttheoryanswerprofile "
                                                   + "set answered = '" + 
                                                   answerCollectorArrayList.get(pos).toString() + "' "
                                                      + "where registrationnumber = '" + 
                                                  registrationNumberField.getText().toLowerCase() + "' "
                                                      + "and testsubject = '" + getTestSubject + "' "
                                                      + "and testclass = '" + getTestClass + "' "
                                                      + "and testterm = '" + getTestTerm + "' "
                                                      + "and testsession = '" + getTestSession + "' "
                                                      + "and question = '" + questionArrayList.get(pos).toString() + "' "
                                                      + "and questionmode = '" + getQuestionMode + "' "
                                                      + "and datewritten = '" + (day + " " + 
                                                      letterMonth(Integer.parseInt(mon)).toLowerCase() + 
                                                      " 20" + year) + "'");
                                        }
                                        else {
                                            //
                                            answerCollectorArrayList.set(pos, 
                                                            theoryAnswerLabArea.getText());
                                            //
                                            for(int col = 0; col < smartQuestionField.length; col++) {
                                                if((Integer.parseInt(smartQuestionField[col].getText()) - 1) 
                                                        == pos) {
                                                    smartQuestionField[pos].setBackground(Color.CYAN);
                                                    break;
                                                }
                                            }
                                            //
                                            int count = (getNumberOfDetails("select * "
                                                        + "from studenttheoryanswerprofile") + 1);
                                            //
                                            insertUpdateDelete("insert into studenttheoryanswerprofile "
                                                + "values ('" + count + "', "
                                                        + "'" + registrationNumberField.getText().toLowerCase() + "', "
                                                        + "'" + getTestSubject + "', "
                                                        + "'" + getTestClass + "', "
                                                        + "'" + getTestTerm + "', "
                                                        + "'" + getTestSession + "', "
                                                        + "'" + questionArrayList.get(pos).toString() + "', "
                                                        + "'" + answerCollectorArrayList.get(pos).toString() + "', "
                                                        + "'" + getQuestionMode + "', "
                                                        + "'" + (day + " " + 
                                                    letterMonth(Integer.parseInt(mon)).toLowerCase() + 
                                                    " 20" + year) + "')");
                                        }
                                        //
                                        answeredQuestionCounter = 0;
                                        for(int row = 0; row < answerCollectorArrayList.size(); row++) {
                                            if(!answerCollectorArrayList.get(row).toString().
                                                equalsIgnoreCase("")) {
                                                //
                                                answeredQuestionCounter++;
                                            }
                                        }
                                        //
                                        totalAnsweredLab.setText(answeredQuestionCounter + 
                                                " Question(s) Answered  ");
                                        //
                                        saveTheoryButton.setEnabled(false);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    //
                    void loadEachQuestion() {
                        //
                        if(getQuestionMode.equalsIgnoreCase("objective")) {
                            for(int pos = 0; pos < questionArrayList.size(); pos++) {
                                if(pos == movementControl) {
                                    String qText = questionArrayList.get(pos).toString();
                                    //
                                    if(qText.startsWith("//" + getServerIP + "/")) {
                                        //
                                        questionLab.setIcon(null);
                                        questionLab.setIcon(new ImageIcon(qText));
                                        //
                                        questionSectionPanel.removeAll();
                                        questionSectionPanel.add(questionLab);
                                        validate();
                                    }
                                    else {
                                        questionLabArea.setText(qText);
                                        //
                                        questionSectionPanel.removeAll();
                                        questionSectionPanel.add(questionLabArea);
                                        validate();
                                    }
                                    //
                                    centQuestionSectionPanel.setBorder(
                                        BorderFactory.createTitledBorder(null,
                                        " Question " + (movementControl + 1) + " ",
                                        TitledBorder.LEFT, 0, new Font(getFontType, 
                                            Font.BOLD, 10), Color.RED));
                                    //
                                    centOptionSectionPanel.setBorder(
                                                BorderFactory.createEtchedBorder());
                                    //
                                    break;
                                }
                            }
                        }
                        else if(getQuestionMode.equalsIgnoreCase("theory")) {
                            for(int pos = 0; pos < questionArrayList.size(); pos++) {
                                if(pos == movementControl) {
                                    //
                                    if(getNumberOfDetails("select * from studenttheoryanswerprofile "
                                            + "where registrationnumber = '" + 
                                            registrationNumberField.getText().toLowerCase() + "' "
                                            + "and testsubject = '" + getTestSubject + "' "
                                            + "and testclass = '" + getTestClass + "' "
                                            + "and testterm = '" + getTestTerm + "' "
                                            + "and testsession = '" + getTestSession + "' "
                                            + "and question = '" + questionArrayList.get(pos).toString() + "' "
                                            + "and questionmode = '" + getQuestionMode + "' "
                                            + "and datewritten = '" + (day + " " + 
                                            letterMonth(Integer.parseInt(mon)).toLowerCase() + 
                                            " 20" + year) + "'") > 0) {
                                        //
                                        String qText = questionArrayList.get(pos).toString();
                                        //
                                        if(qText.startsWith("//" + getServerIP + "/")) {
                                            //
                                            questionLab.setIcon(null);
                                            questionLab.setIcon(new ImageIcon(qText));
                                            //
                                            questionSectionPanel.removeAll();
                                            questionSectionPanel.add(questionLab);
                                            validate();
                                        }
                                        else {
                                            questionLabArea.setText(qText);
                                            //
                                            questionSectionPanel.removeAll();
                                            questionSectionPanel.add(questionLabArea);
                                            validate();
                                        }
                                    }
                                    else {
                                        String qText = questionArrayList.get(pos).toString();
                                        //
                                        if(qText.startsWith("//" + getServerIP + "/")) {
                                            //
                                            questionLab.setIcon(null);
                                            questionLab.setIcon(new ImageIcon(qText));
                                            //
                                            questionSectionPanel.removeAll();
                                            questionSectionPanel.add(questionLab);
                                            validate();
                                        }
                                        else {
                                            questionLabArea.setText(qText);
                                            //
                                            questionSectionPanel.removeAll();
                                            questionSectionPanel.add(questionLabArea);
                                            validate();
                                        }
                                    }
                                    //
                                    centQuestionSectionPanel.setBorder(
                                        BorderFactory.createTitledBorder(null,
                                        " Question " + (movementControl + 1) + " ",
                                        TitledBorder.LEFT, 0, new Font(getFontType, 
                                            Font.BOLD, 10), Color.RED));
                                    //
                                    centOptionSectionPanel.setBorder(
                                        BorderFactory.createTitledBorder(null,
                                        " Type Response To Question " + 
                                            (movementControl + 1) + " Here ",
                                        TitledBorder.LEFT, 0, new Font(getFontType, 
                                            Font.BOLD, 10), Color.RED));
                                    //
                                    break;
                                }
                            }   
                        }
                    }
                    //
                    void loadAnswerToEachQuestion() {
                        if(getQuestionMode.equalsIgnoreCase("objective")) {
                            optionARadioButton.setSelected(false);
                            optionARadioButton.setFont(new Font(getFontType, Font.PLAIN, 14));
                            optionALab.setFont(new Font(getFontType, Font.PLAIN, 14));
                            optionBRadioButton.setSelected(false);
                            optionBRadioButton.setFont(new Font(getFontType, Font.PLAIN, 14));
                            optionBLab.setFont(new Font(getFontType, Font.PLAIN, 14));
                            optionCRadioButton.setSelected(false);
                            optionCRadioButton.setFont(new Font(getFontType, Font.PLAIN, 14));
                            optionCLab.setFont(new Font(getFontType, Font.PLAIN, 14));
                            optionDRadioButton.setSelected(false);
                            optionDRadioButton.setFont(new Font(getFontType, Font.PLAIN, 14));
                            optionDLab.setFont(new Font(getFontType, Font.PLAIN, 14));
                            optionERadioButton.setSelected(false);
                            optionERadioButton.setFont(new Font(getFontType, Font.PLAIN, 14));
                            optionELab.setFont(new Font(getFontType, Font.PLAIN, 14));
                            //
                            if(!answerCollectorArrayList.get(movementControl).toString().
                                    equalsIgnoreCase("")) {
                                //
                                String getOpt = answerCollectorArrayList.get(movementControl).
                                        toString();
                                if(getOpt.equalsIgnoreCase("a")) {
                                    optionALab.setFont(new Font(getFontType, Font.BOLD, 14));
                                    optionARadioButton.setSelected(true);
                                }
                                else if(getOpt.equalsIgnoreCase("b")) {
                                    optionBLab.setFont(new Font(getFontType, Font.BOLD, 14));
                                    optionBRadioButton.setSelected(true);
                                }
                                else if(getOpt.equalsIgnoreCase("c")) {
                                    optionCLab.setFont(new Font(getFontType, Font.BOLD, 14));
                                    optionCRadioButton.setSelected(true);
                                }
                                else if(getOpt.equalsIgnoreCase("d")) {
                                    optionDLab.setFont(new Font(getFontType, Font.BOLD, 14));
                                    optionDRadioButton.setSelected(true);
                                }
                                else if(getOpt.equalsIgnoreCase("e")) {
                                    optionELab.setFont(new Font(getFontType, Font.BOLD, 14));
                                    optionERadioButton.setSelected(true);
                                }
                            }
                            //
                            for(int pos = 0; pos <= questionArrayList.size(); pos++) {
                                if(pos == movementControl) {
                                    String qText = answerOptionAArrayList.get(pos).toString();
                                    //
                                    if(qText.startsWith("//" + getServerIP + "/")) {
                                        //
                                        optionALab.setText("");
                                        optionALab.setIcon(new ImageIcon(qText));
                                    }
                                    else {
                                        optionALab.setIcon(null);
                                        optionALab.setText(qText);
                                    }
                                    //
                                    qText = answerOptionBArrayList.get(pos).toString();
                                    //
                                    if(qText.startsWith("//" + getServerIP + "/")) {
                                        //
                                        optionBLab.setText("");
                                        optionBLab.setIcon(new ImageIcon(qText));
                                    }
                                    else {
                                        optionBLab.setIcon(null);
                                        optionBLab.setText(qText);
                                    }
                                    //
                                    qText = answerOptionCArrayList.get(pos).toString();
                                    //
                                    if(qText.startsWith("//" + getServerIP + "/")) {
                                        //
                                        optionCLab.setText("");
                                        optionCLab.setIcon(new ImageIcon(qText));
                                    }
                                    else {
                                        optionCLab.setIcon(null);
                                        optionCLab.setText(qText);
                                    }
                                    //
                                    qText = answerOptionDArrayList.get(pos).toString();
                                    //
                                    if(qText.startsWith("//" + getServerIP + "/")) {
                                        //
                                        optionDLab.setText("");
                                        optionDLab.setIcon(new ImageIcon(qText));
                                    }
                                    else {
                                        optionDLab.setIcon(null);
                                        optionDLab.setText(qText);
                                    }
                                    //
                                    qText = answerOptionEArrayList.get(pos).toString();
                                    //
                                    if(qText.startsWith("//" + getServerIP + "/")) {
                                        //
                                        optionELab.setText("");
                                        optionELab.setIcon(new ImageIcon(qText));
                                    }
                                    else {
                                        optionELab.setIcon(null);
                                        optionELab.setText(qText);
                                    }
                                    //
                                    break;
                                }
                            }
                        }
                        else if(getQuestionMode.equalsIgnoreCase("theory")) {
                            for(int pos = 0; pos < questionArrayList.size(); pos++) {
                                if(pos == movementControl) {
                                    //
                                    if(getNumberOfDetails("select * from studenttheoryanswerprofile "
                                            + "where registrationnumber = '" + 
                                            registrationNumberField.getText().toLowerCase() + "' "
                                            + "and testsubject = '" + getTestSubject + "' "
                                            + "and testclass = '" + getTestClass + "' "
                                            + "and testterm = '" + getTestTerm + "' "
                                            + "and testsession = '" + getTestSession + "' "
                                            + "and question = '" + questionArrayList.get(pos).toString() + "' "
                                            + "and questionmode = '" + getQuestionMode + "' "
                                            + "and datewritten = '" + (day + " " + 
                                            letterMonth(Integer.parseInt(mon)).toLowerCase() + 
                                            " 20" + year) + "'") > 0) {
                                         //
                                         theoryAnswerLabArea.setText(
                                                 answerCollectorArrayList.get(pos).toString());
                                    }
                                    else {
                                        //no existing response
                                        theoryAnswerLabArea.setText("no response");
                                    }
                                    break;
                                }
                            }
                        }
                    }
                    //
                    class startTestTimer extends Thread {
                        
                        public void run() {
                            int minCt = (Integer.parseInt(testDuration) - 1);
                            int secCt = 59;
                            //
                            while(timerControl) {
                                //
                                timeField.setText(
                                        (new String("" + minCt).length() == 1 ? "0" + 
                                        minCt : minCt) + " : " +  
                                        (new String("" + secCt).length() == 1 ? "0" + 
                                        secCt : secCt));
                                //
                                if(minCt <= 4 && secCt <= 59) {
                                    timeField.setBackground(Color.WHITE);
                                    timeField.setForeground(Color.RED);
                                    //
                                    try{sleep(500);}catch(InterruptedException erer){}
                                    //
                                    timeField.setText(
                                            (new String("" + minCt).length() == 1 ? "0" + 
                                            minCt : minCt) + " : " + 
                                            (new String("" + secCt).length() == 1 ? "0" + 
                                            secCt : secCt));
                                    //
                                    timeField.setBackground(Color.RED);
                                    timeField.setForeground(Color.WHITE);
                                    //
                                    try{sleep(500);}catch(InterruptedException erer){}
                                    //
                                    secCt--;
                                    if(secCt == 0) {
                                        minCt--;
                                        secCt = 59;
                                    }
                                    //
                                    if(minCt == 0 && secCt == 1) {
                                        //auto-submit test
                                        timerControl = false;
                                        //
                                        endStudentTest();
                                        break;
                                    }
                                }
                                else {
                                    //
                                    timeField.setBackground(Color.WHITE);
                                    timeField.setForeground(Color.RED);
                                    //
                                    try{sleep(1000);}catch(InterruptedException erer){}
                                    //
                                    timeField.setText(
                                            (new String("" + minCt).length() == 1 ? "0" + 
                                            minCt : minCt) + " : " +  
                                            (new String("" + secCt).length() == 1 ? "0" + 
                                            secCt : secCt));
                                    //
                                    secCt--;
                                    if(secCt == 0) {
                                        minCt--;
                                        secCt = 59;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        //
        class cbTestingSetupPanel extends JPanel {
            
            JPanel northPanel = new JPanel(),
                    southPanel = new JPanel(),
                    centerPanel = new JPanel();
            //
            JPanel formPicturePanel = new JPanel(),
                    testingDisplayPanel = new JPanel();
            //
            JPanel questionOptionPanel = new JPanel();
            JLabel testSetupLab = new JLabel("Test Schedule"),
                    testScoresLab = new JLabel("Scores"),
                    testReportLab = new JLabel("Report");
            //
            JPanel buttonPanel = new JPanel(),
                    centbuttonPanel = new JPanel(),
                    centFormPicturePanel = new JPanel(),
                    centFormPanel = new JPanel();
            //view
            JTextField[] testClassField = null,
                    testTermField = null,
                    testSessionField = null,
                    testSubjectField = null,
                    numberOfTestQuestionField = null,
                    testDurationField = null,
                    testQuestionBatchField = null;
            //
            JCheckBox[] testCheckBox = null;
            JButton[] testSetupButton = null,
                    testDeleteButton = null;
            //
            JLabel selectClassLab = new JLabel("Select Class: "),
                    selectTermControlLab = new JLabel(" Term: "),
                    selectSessionLab = new JLabel(" Sess/Year: "),
                    selectBatchLab = new JLabel(" Batch: "),
                    selectSubjectControlLab = new JLabel(" Subject: "),
                    selectQuestionModeLab = new JLabel("Mode: ");
            //
            String[] viewTestClassList = null,
                    viewTestStudentList = null,
                    viewTestSubjectList = null,
                    viewTestYearList = null,
                    viewTestTermList = null,
                    viewTestQuestionModeList = {"OBJECTIVE", "THEORY"};
            //
            JComboBox classBox = null,
                    termBox = null,
                    subjectBox = null,
                    yearBox = null,
                    questionModeBox = new JComboBox(viewTestQuestionModeList),
                    subjectClassBox = null;
            //
            JButton searchButton = new JButton("Search Now"),
                    exportButton = new JButton("Export Sheet");
            //
            JLabel searchStudentID = new JLabel(
                    new ImageIcon("images/searchpic.png"));
            //
            String getTestClass = "",
                    getTestTerm = "",
                    getTestSubject = "",
                    getTestSession = "",
                    getQuestionMode = "",
                    getTestScheduleAS = "",
                    getQuestionBatch = "";//eval the populat
            //
            JPanel classBoxPanel = new JPanel();
            //
            int resCount = 0;
            String[] resList = null;
            //
            JPanel refreshLabClassBoxPanel = new JPanel();
            //
            String scoreReportControl = "";
            //
            public cbTestingSetupPanel() {
                //
                southPanel.setBackground(themeColor);
                centerPanel.setBackground(themeColor);
                northPanel.setBackground(themeColor);
                northPanel.setBorder(BorderFactory.createEtchedBorder());
                //
                JLabel loginDetails = new JLabel("Login As: "
                        + getLoginUserName.toUpperCase() + "   ");
                loginDetails.setFont(new Font(getFontType, Font.BOLD, 14));
                loginDetails.setForeground(Color.ORANGE);
                //
                testSetupLab.setForeground(Color.BLUE);
                testSetupLab.setFont(new Font(getFontType, Font.PLAIN, 12));
                testSetupLab.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                testScoresLab.setForeground(Color.BLUE);
                testScoresLab.setFont(new Font(getFontType, Font.PLAIN, 12));
                testScoresLab.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                testReportLab.setForeground(Color.BLUE);
                testReportLab.setFont(new Font(getFontType, Font.PLAIN, 12));
                testReportLab.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                //
                questionOptionPanel.setBackground(themeColor);
                questionOptionPanel.add(new JLabel("   "));
                questionOptionPanel.add(testSetupLab);
                questionOptionPanel.add(new JLabel(" | "));
                questionOptionPanel.add(testScoresLab);
                questionOptionPanel.add(new JLabel(" | "));
                questionOptionPanel.add(testReportLab);
                //
                JPanel optionPanel = new JPanel();
                optionPanel.setLayout(new BorderLayout());
                optionPanel.setBackground(themeColor);
                optionPanel.setBorder(BorderFactory.createEtchedBorder());
                optionPanel.add(loginDetails, BorderLayout.EAST);
                optionPanel.add(questionOptionPanel, BorderLayout.WEST);
                //
                northPanel.setLayout(new BorderLayout());
                northPanel.add(optionPanel, BorderLayout.NORTH);
                northPanel.add(new JLabel(" "), BorderLayout.SOUTH);
                //
                int ct = getNumberOfDetails("select * from classprofile");
                if (ct == 0) {
                    classBox = new JComboBox();
                } else {
                    viewTestClassList =
                            populateList("select classname from classprofile "
                            + "order by classname", ct);
                    for (int pos = 0; pos < viewTestClassList.length; pos++) {
                        viewTestClassList[pos] = viewTestClassList[pos].toUpperCase();
                    }
                    classBox = new JComboBox(viewTestClassList);
                }
                //
                ct = getNumberOfDetails("select * from termprofile");
                if (ct == 0) {
                    termBox = new JComboBox();
                } else {
                    viewTestTermList =
                            populateList("select termname from termprofile "
                            + "order by termname", ct);
                    for (int pos = 0; pos < viewTestTermList.length; pos++) {
                        viewTestTermList[pos] = viewTestTermList[pos].toUpperCase();
                    }
                    termBox = new JComboBox(viewTestTermList);
                }
                //
                ct = getNumberOfDetails("select * from sessionprofile");
                if (ct == 0) {
                    yearBox = new JComboBox();
                } else {
                    viewTestYearList =
                            populateList("select sessionname "
                            + "from sessionprofile", ct);
                    //
                    yearBox = new JComboBox(viewTestYearList);
                }
                //
                ct = getNumberOfDetails("select * from subjectprofile");
                if (ct == 0) {
                    subjectBox = new JComboBox();
                } else {
                    viewTestSubjectList =
                            populateList("select subjectname from subjectprofile "
                            + "order by subjectname", ct);
                    for (int pos = 0; pos < viewTestSubjectList.length; pos++) {
                        viewTestSubjectList[pos] = viewTestSubjectList[pos].toUpperCase();
                    }
                    subjectBox = new JComboBox(viewTestSubjectList);
                }
                //
                searchButton.setForeground(Color.BLUE);
                searchButton.setBackground(themeColor);
                searchButton.setFont(new Font(getFontType, Font.PLAIN, 10));
                exportButton.setForeground(Color.BLUE);
                exportButton.setBackground(themeColor);
                exportButton.setFont(new Font(getFontType, Font.PLAIN, 10));
                classBox.setForeground(Color.BLACK);
                classBox.setFont(new Font(getFontType, Font.PLAIN, 10));
                termBox.setForeground(Color.BLACK);
                termBox.setFont(new Font(getFontType, Font.PLAIN, 10));
                yearBox.setForeground(Color.BLACK);
                yearBox.setFont(new Font(getFontType, Font.PLAIN, 10));
                subjectBox.setForeground(Color.BLACK);
                subjectBox.setFont(new Font(getFontType, Font.PLAIN, 10));
                questionModeBox.setForeground(Color.BLACK);
                questionModeBox.setFont(new Font(getFontType, Font.PLAIN, 10));
                selectClassLab.setFont(new Font(getFontType, Font.BOLD, 10));
                selectClassLab.setForeground(Color.RED);
                selectTermControlLab.setFont(new Font(getFontType, Font.BOLD, 10));
                selectTermControlLab.setForeground(Color.RED);
                selectSessionLab.setFont(new Font(getFontType, Font.BOLD, 10));
                selectSessionLab.setForeground(Color.RED);
                selectSubjectControlLab.setFont(new Font(getFontType, Font.BOLD, 10));
                selectSubjectControlLab.setForeground(Color.RED);
                selectQuestionModeLab.setFont(new Font(getFontType, Font.BOLD, 10));
                selectQuestionModeLab.setForeground(Color.RED);
                commTestLab.setForeground(Color.RED);
                commTestLab.setFont(new Font(getFontType, Font.BOLD, 10));
                searchStudentID.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                searchStudentID.setToolTipText("Search One Student Score");
                //
                classBoxPanel.setBackground(Color.WHITE);
                classBoxPanel.add(selectClassLab);
                classBoxPanel.add(classBox);
                classBoxPanel.add(selectTermControlLab);
                classBoxPanel.add(termBox);
                classBoxPanel.add(selectSessionLab);
                classBoxPanel.add(yearBox);
                classBoxPanel.add(selectSubjectControlLab);
                classBoxPanel.add(subjectBox);
                classBoxPanel.add(selectQuestionModeLab);
                classBoxPanel.add(questionModeBox);
                classBoxPanel.add(new JLabel(" | "));
                classBoxPanel.add(searchStudentID);
                classBoxPanel.add(new JLabel("   "));
                classBoxPanel.add(searchButton);
                classBoxPanel.add(new JLabel(" "));
                classBoxPanel.add(exportButton);
                //
                JPanel centClassBoxPanel = new JPanel();
                centClassBoxPanel.setBackground(Color.WHITE);
                centClassBoxPanel.setBorder(BorderFactory.createEtchedBorder());
                centClassBoxPanel.setLayout(new BorderLayout());
                centClassBoxPanel.add(classBoxPanel, BorderLayout.WEST);
                //
                testingDisplayPanel.setLayout(new BorderLayout());
                testingDisplayPanel.setBackground(themeColor);
                //
                JPanel commTestDisplayPanel = new JPanel();
                commTestDisplayPanel.setLayout(new BorderLayout());
                commTestDisplayPanel.setBorder(
                        BorderFactory.createTitledBorder(null,
                        " Available Setup/Scores/Report. Check To Make Correction ",
                        TitledBorder.LEFT, 0, new Font(getFontType, Font.BOLD, 10), Color.RED));
                commTestDisplayPanel.add(
                        new JScrollPane(testingDisplayPanel), BorderLayout.CENTER);
                commTestDisplayPanel.add(commTestLab, BorderLayout.SOUTH);
                //
                centerPanel.setLayout(new BorderLayout());
                centerPanel.add(centClassBoxPanel, BorderLayout.NORTH);
                centerPanel.add(commTestDisplayPanel, BorderLayout.CENTER);
                //
                setLayout(new BorderLayout());
                add(northPanel, BorderLayout.NORTH);
                add(centerPanel, BorderLayout.CENTER);
                validate();
                //
                classBox.addActionListener(new viewTestClassControl());
                termBox.addActionListener(new viewTestTermControl());
                yearBox.addActionListener(new viewTestYearControl());
                subjectBox.addActionListener(new viewTestSubjectControl());
                questionModeBox.addActionListener(new viewTestQuestionModeControl());
                testSetupLab.addMouseListener(new newTestControl());
                testScoresLab.addMouseListener(new viewTestScoreControl());
                testReportLab.addMouseListener(new viewTestReportControl());
                searchButton.addActionListener(new searchTestScoreReportControl());
                exportButton.addActionListener(new exportToExcelControl());
                searchStudentID.addMouseListener(new viewAStudentControl());
            }
            //
            class viewAStudentControl extends MouseAdapter {
                
                public void mouseClicked(MouseEvent e) {
                    //
                    if (!getTestClass.equalsIgnoreCase("") &&
                            !getTestTerm.equalsIgnoreCase("") &&
                            !getTestSubject.equalsIgnoreCase("") &&
                            !getQuestionMode.equalsIgnoreCase("") &&
                            !getTestSession.equalsIgnoreCase("")) {
                        //
                        if(scoreReportControl.equalsIgnoreCase("score")) {
                            if (getNumberOfDetails("select * from testscoresprofile") > 0) {
                                //
                                promptLab.setText("Enter Student Name OR Registration Number");
                                String getOneStudentEntry = JOptionPane.showInputDialog(null, 
                                        promptLab);
                                //
                                new viewScoreReport(getTestClass, getTestTerm,
                                        getTestSession, getTestSubject,
                                        scoreReportControl, getQuestionMode, 
                                        getOneStudentEntry).start(); 
                            }
                            else {
                                promptLab.setText("No Test Scores Available Yet For Student");
                                JOptionPane.showMessageDialog(null,
                                        promptLab,
                                        getSchoolName.toUpperCase(),
                                        JOptionPane.INFORMATION_MESSAGE,
                                        infoIcon);
                            }
                        }
                    }
                    else {
                        promptLab.setText("Select Test Parameter And Search Student Again");
                        JOptionPane.showMessageDialog(null,
                                promptLab,
                                getSchoolName.toUpperCase(),
                                JOptionPane.INFORMATION_MESSAGE,
                                infoIcon);
                    }
                }
            }
            //
            class viewTestQuestionModeControl implements ActionListener {
                
                public void actionPerformed(ActionEvent e) {
                    for(int pos = 0; pos < viewTestQuestionModeList.length; pos++) {
                        if(pos == questionModeBox.getSelectedIndex()) {
                            getQuestionMode = viewTestQuestionModeList[pos].toLowerCase();
                        }
                    }
                }
            }
            //
            class exportToExcelControl implements ActionListener {
                
                public void actionPerformed(ActionEvent e) {
                    //
                    if(resCount == 0) {
                        //
                        promptLab.setText("No Test Related Summary To Export. "
                                + "Please Make Preview.");
                        JOptionPane.showMessageDialog(null, 
                                promptLab,
                                getSchoolName.toUpperCase(),
                                JOptionPane.INFORMATION_MESSAGE,
                                infoIcon);
                    }
                    else {
                        sendToExcel(getTestClass, getTestTerm,
                                getTestSession, getTestSubject, 
                                getQuestionMode);
                    }
                }
            }
            //
            class searchTestScoreReportControl implements ActionListener {
                
                public void actionPerformed(ActionEvent e) {
                    //
                    if (!getTestClass.equalsIgnoreCase("") &&
                            !getTestTerm.equalsIgnoreCase("") &&
                            !getTestSubject.equalsIgnoreCase("") &&
                            !getQuestionMode.equalsIgnoreCase("") &&
                            !getTestSession.equalsIgnoreCase("")) {
                        //
                        if(scoreReportControl.equalsIgnoreCase("score") || 
                            scoreReportControl.equalsIgnoreCase("report")) {
                            if (getNumberOfDetails("select * from testscoresprofile") > 0) {
                                //
                                new viewScoreReport(getTestClass, getTestTerm,
                                        getTestSession, getTestSubject,
                                        scoreReportControl, getQuestionMode,
                                        "").start(); 
                            }
                            else {
                                promptLab.setText("No Test Scores Available Yet!");
                                JOptionPane.showMessageDialog(null,
                                        promptLab,
                                        getSchoolName.toUpperCase(),
                                        JOptionPane.INFORMATION_MESSAGE,
                                        infoIcon);
                            }
                        }
                        else if(scoreReportControl.equalsIgnoreCase("setup")) {
                            if (getNumberOfDetails("select * from loadtestprofile") > 0) {
                                //
                                testingDisplayPanel.removeAll();
                                testingDisplayPanel.add(new JLabel(
                                                new ImageIcon("images/loadicon.gif")));
                                //
                                new viewTestSetup(getTestTerm, getTestSession, 
                                        getQuestionMode, getTestClass, 
                                        getTestSubject).start();
                            }
                            else {
                                promptLab.setText("No Test Schedule Available Yet!");
                                JOptionPane.showMessageDialog(null,
                                        promptLab,
                                        getSchoolName.toUpperCase(),
                                        JOptionPane.INFORMATION_MESSAGE,
                                        infoIcon);
                            }
                        }
                        else {
                            promptLab.setText("Please Click Operational Category");
                            JOptionPane.showMessageDialog(null,
                                    promptLab,
                                    getSchoolName.toUpperCase(),
                                    JOptionPane.INFORMATION_MESSAGE,
                                    infoIcon);
                        }
                        validate();
                    } 
                    else {
                        promptLab.setText("Select Class, Term, Session And Subject. "
                                + "Then Search Again.");
                        JOptionPane.showMessageDialog(null,
                                promptLab,
                                getSchoolName.toUpperCase(),
                                JOptionPane.INFORMATION_MESSAGE,
                                infoIcon);
                    }
                } 
            }
            //
            class viewTestReportControl extends MouseAdapter {
                
                public void mouseClicked(MouseEvent e) {
                    //
                    testSetupLab.setForeground(Color.BLUE);
                    testScoresLab.setForeground(Color.BLUE);
                    testReportLab.setForeground(Color.RED);
                    //
                    formPicturePanel.removeAll();
                    centFormPanel.removeAll();
                    buttonPanel.removeAll();
                    centFormPicturePanel.removeAll();
                    centbuttonPanel.removeAll();
                    //
                    validate();
                    scoreReportControl = "report";
                }
            }
            //
            class viewTestScoreControl extends MouseAdapter {
                
                public void mouseClicked(MouseEvent e) {
                    //
                    testSetupLab.setForeground(Color.BLUE);
                    testScoresLab.setForeground(Color.RED);
                    testReportLab.setForeground(Color.BLUE);
                    //
                    formPicturePanel.removeAll();
                    centFormPanel.removeAll();
                    buttonPanel.removeAll();
                    centFormPicturePanel.removeAll();
                    centbuttonPanel.removeAll();
                    //
                    validate();
                    scoreReportControl = "score";
                }
            }
            //
            class newTestControl extends MouseAdapter {

                String[] questionParaList = {"SELECT TERM:  ", 
                                "SELECT CLASS:  ", 
                                "SELECT SESSION/YEAR:  ", 
                                "SELECT SUBJECT:  ",
                                "SCORE PER QUESTION:  ",
                                "NUMBER OF TEST QUESTIONS:  ",
                                "TEST DURATION:  ", 
                                "SELECT QUESTION MODE:  ",
                                "QUESTION BATCH:  ",
                                "SCHEDULE AS:  "};
                //
                JLabel[] questionParaLab = new JLabel[questionParaList.length];
                //
                String[] questionParaModeList = {"OBJECTIVE", "THEORY"},
                        scheduleAsList = {"CA/EXAMINATION", "REGULAR PRACTICE"};
                //
                JComboBox classParaBox = null,
                        termParaBox = null,
                        sessionParaBox = null,
                        subjectParaBox = null,
                        questionBatchParaBox = null,
                        questionParaModeBox = new JComboBox(questionParaModeList),
                        scheduleAsBox = new JComboBox(scheduleAsList);
                //
                String[] classParaList = null,
                        termParaList = null,
                        sessionParaList = null,
                        subjectParaList = null,
                        questionBatchParaList = null;
                //
                JTextField numberOfQuestionField = new JTextField(5),
                        scorePerQuestionField = new JTextField(5),
                        questionTestMinuteDurationField = new JTextField(5);
                //
                JLabel questionTestMinuteDurationLab = new JLabel(" Minutes     "
                        + "               ");
                //
                JButton continueParaButton = new JButton("Save Setup",
                        new ImageIcon("images/next.jpg"));
                //
                private JPanel formPanel = new JPanel();
                //
                JLabel messageDisplayLab = new JLabel("");
                JCheckBox displayStudentTestScoreCheckBox = 
                        new JCheckBox("Enable Student Test Score");
                //
                String getDisplayStudentTestScoreCheckBox = "no";
                //
                public void mouseClicked(MouseEvent e) {
                    //
                    testSetupLab.setForeground(Color.RED);
                    testScoresLab.setForeground(Color.BLUE);
                    testReportLab.setForeground(Color.BLUE);
                    //
                    scoreReportControl = "setup";
                    //
                    formPanel.removeAll();
                    formPicturePanel.removeAll();
                    centFormPanel.removeAll();
                    buttonPanel.removeAll();
                    centFormPicturePanel.removeAll();
                    centbuttonPanel.removeAll();
                    //
                    if(getNumberOfDetails("select * from termprofile") == 0) {
                        insertUpdateDelete("insert into termprofile "
                                + "values ('first')");
                        insertUpdateDelete("insert into termprofile "
                                + "values ('second')");
                        insertUpdateDelete("insert into termprofile "
                                + "values ('third')");
                    }
                    //
                    int ct = getNumberOfDetails("select * from classprofile");
                    if (ct == 0) {
                        classParaBox = new JComboBox();
                    } 
                    else {
                        classParaList = populateList("select classname from classprofile "
                                + "order by classname", ct);
                        //
                        for (int pos = 0; pos < classParaList.length; pos++) {
                            classParaList[pos] = classParaList[pos].toUpperCase();
                        }
                        classParaBox = new JComboBox(classParaList);
                    }
                    //
                    ct = getNumberOfDetails("select distinct questionbatchnumber "
                            + "from questionprofile");
                    if (ct == 0) {
                        questionBatchParaBox = new JComboBox();
                    } 
                    else {
                        questionBatchParaList = 
                                populateList("select distinct questionbatchnumber "
                                + "from questionprofile "
                                + "order by questionid", ct);
                        //
                        questionBatchParaBox = new JComboBox(questionBatchParaList);
                    }
                    //
                    ct = getNumberOfDetails("select * from termprofile");
                    if (ct == 0) {
                        termParaBox = new JComboBox();
                    } 
                    else {
                        termParaList = populateList("select termname from termprofile "
                                + "order by termname", ct);
                        //
                        for (int pos = 0; pos < termParaList.length; pos++) {
                            termParaList[pos] = termParaList[pos].toUpperCase();
                        }
                        termParaBox = new JComboBox(termParaList);
                    }
                    //
                    ct = getNumberOfDetails("select * from sessionprofile");
                    if (ct == 0) {
                        sessionParaBox = new JComboBox();
                    } 
                    else {
                        sessionParaList = 
                                populateList("select sessionname from sessionprofile "
                                + "order by sessionname", ct);
                        //
                        sessionParaBox = new JComboBox(sessionParaList);
                    }
                    //
                    ct = getNumberOfDetails("select * from subjectprofile");
                    if (ct == 0) {
                        subjectParaBox = new JComboBox();
                    } 
                    else {
                        subjectParaList = 
                                populateList("select subjectname from subjectprofile "
                                + "order by subjectname", ct);
                        //
                        for (int pos = 0; pos < subjectParaList.length; pos++) {
                            subjectParaList[pos] = subjectParaList[pos].toUpperCase();
                        }
                        subjectParaBox = new JComboBox(subjectParaList);
                    }
                    //
                    formPanel.setLayout(new GridLayout(
                            questionParaList.length + 1, 2, 0, 1));
                    formPanel.setBorder(
                            BorderFactory.createTitledBorder(null,
                            " Test Parameter Setup Section ",
                            TitledBorder.LEFT, 0, new Font(getFontType, Font.BOLD, 10), 
                            Color.BLUE));
                    //
                    ct = 0;
                    for (int pos = 0; pos < questionParaList.length; pos++) {
                        questionParaLab[pos] = new JLabel(questionParaList[pos], JLabel.RIGHT);
                        questionParaLab[pos].setFont(new Font(getFontType, Font.BOLD, 10));
                        questionParaLab[pos].setForeground(Color.RED);
                    }
                    //
                    refreshLabClassBoxPanel.setBackground(themeColor);
                    refreshLabClassBoxPanel.setLayout(new BorderLayout());
                    refreshLabClassBoxPanel.add(classParaBox, BorderLayout.WEST);
                    //
                    formPanel.add(questionParaLab[0]);
                    formPanel.add(termParaBox);
                    //
                    formPanel.add(questionParaLab[1]);
                    formPanel.add(classParaBox);
                    //
                    formPanel.add(questionParaLab[2]);
                    formPanel.add(sessionParaBox);
                    //
                    formPanel.add(questionParaLab[3]);
                    formPanel.add(subjectParaBox);
                    //
                    formPanel.add(questionParaLab[4]);
                    formPanel.add(scorePerQuestionField);
                    //
                    formPanel.add(questionParaLab[5]);
                    formPanel.add(numberOfQuestionField);
                    //
                    scorePerQuestionField.setFont(new Font(getFontType, Font.PLAIN, 12));
                    scorePerQuestionField.setForeground(Color.BLACK);
                    scorePerQuestionField.setBorder(BorderFactory.createEtchedBorder());
                    numberOfQuestionField.setFont(new Font(getFontType, Font.PLAIN, 12));
                    numberOfQuestionField.setForeground(Color.BLACK);
                    numberOfQuestionField.setBorder(BorderFactory.createEtchedBorder());
                    questionTestMinuteDurationLab.setFont(new Font(getFontType, Font.PLAIN, 12));
                    questionTestMinuteDurationLab.setForeground(Color.BLUE);
                    questionTestMinuteDurationField.setFont(new Font(getFontType, Font.PLAIN, 14));
                    questionTestMinuteDurationField.setForeground(Color.BLACK);
                    questionTestMinuteDurationField.setBorder(BorderFactory.createEtchedBorder());
                    displayStudentTestScoreCheckBox.setForeground(Color.RED);
                    displayStudentTestScoreCheckBox.setFont(new Font(getFontType, Font.BOLD, 10));
                    displayStudentTestScoreCheckBox.setBackground(themeColor);
                    //
                    JPanel durationPanel = new JPanel();
                    durationPanel.setBackground(themeColor);
                    durationPanel.add(questionTestMinuteDurationField);
                    durationPanel.add(questionTestMinuteDurationLab);
                    //
                    formPanel.add(questionParaLab[6]);
                    formPanel.add(durationPanel);
                    //
                    formPanel.add(questionParaLab[7]);
                    formPanel.add(questionParaModeBox);
                    //
                    formPanel.add(questionParaLab[8]);
                    formPanel.add(questionBatchParaBox);
                    //
                    formPanel.add(questionParaLab[9]);
                    formPanel.add(scheduleAsBox);
                    //
                    formPanel.add(new JLabel(""));
                    formPanel.add(displayStudentTestScoreCheckBox);
                    //
                    scheduleAsBox.setFont(new Font(getFontType, Font.PLAIN, 12));
                    scheduleAsBox.setForeground(Color.BLACK);
                    classParaBox.setFont(new Font(getFontType, Font.PLAIN, 12));
                    classParaBox.setForeground(Color.BLACK);
                    termParaBox.setFont(new Font(getFontType, Font.PLAIN, 12));
                    termParaBox.setForeground(Color.BLACK);
                    subjectParaBox.setFont(new Font(getFontType, Font.PLAIN, 12));
                    subjectParaBox.setForeground(Color.BLACK);
                    sessionParaBox.setFont(new Font(getFontType, Font.PLAIN, 12));
                    sessionParaBox.setForeground(Color.BLACK);
                    questionBatchParaBox.setFont(new Font(getFontType, Font.PLAIN, 12));
                    questionBatchParaBox.setForeground(Color.BLACK);
                    questionParaModeBox.setFont(new Font(getFontType, Font.PLAIN, 12));
                    questionParaModeBox.setForeground(Color.BLACK);
                    //
                    formPicturePanel.add(formPanel);
                    //
                    continueParaButton.setVerticalTextPosition(AbstractButton.CENTER);
                    continueParaButton.setHorizontalTextPosition(AbstractButton.CENTER);
                    continueParaButton.setFont(new Font(getFontType, Font.BOLD, 10));
                    continueParaButton.setForeground(Color.RED);
                    continueParaButton.setBackground(Color.WHITE);
                    //
                    messageDisplayLab.setText("  Custom Test Schedule");
                    messageDisplayLab.setFont(new Font(getFontType, Font.PLAIN, 12));
                    messageDisplayLab.setForeground(Color.BLACK);
                    //
                    buttonPanel.setLayout(new FlowLayout());
                    buttonPanel.setBackground(Color.WHITE);
                    buttonPanel.add(continueParaButton);
                    //
                    centbuttonPanel.setBackground(Color.WHITE);
                    centbuttonPanel.setLayout(new BorderLayout());
                    centbuttonPanel.setBorder(BorderFactory.createEtchedBorder());
                    centbuttonPanel.add(buttonPanel, BorderLayout.CENTER);
                    centbuttonPanel.add(messageDisplayLab, BorderLayout.WEST);
                    //
                    centFormPicturePanel.setLayout(new BorderLayout());
                    centFormPicturePanel.add(formPicturePanel, BorderLayout.WEST);
                    centFormPicturePanel.add(centbuttonPanel, BorderLayout.SOUTH);
                    //
                    centFormPanel.setLayout(new BorderLayout());
                    centFormPanel.add(centFormPicturePanel, BorderLayout.NORTH);
                    //
                    northPanel.add(centFormPanel, BorderLayout.CENTER);
                    validate();
                    //
                    formPicturePanel.setBackground(themeColor);
                    formPanel.setBackground(themeColor);
                    centFormPicturePanel.setBackground(themeColor);
                    centFormPanel.setBackground(themeColor);
                    //
                    displayStudentTestScoreCheckBox.addItemListener(new enableStudentTestScoreControl());
                    questionBatchParaBox.addActionListener(new selectQuestionBatchControl());
                    sessionParaBox.addActionListener(new selectSessionControl());
                    classParaBox.addActionListener(new selectClassControl());
                    termParaBox.addActionListener(new selectTermControl());
                    subjectParaBox.addActionListener(new selectSubjectControl());
                    questionParaModeBox.addActionListener(new questionModeControl());
                    continueParaButton.addActionListener(new submitTestInfoControl());
                    scheduleAsBox.addActionListener(new scheduleASControl());
                }
                //
                class scheduleASControl implements ActionListener {
                    
                    public void actionPerformed(ActionEvent e) {
                        for (int x = 0; x < scheduleAsList.length; x++) {
                            if (x == scheduleAsBox.getSelectedIndex()) {
                                getTestScheduleAS = scheduleAsList[x].toLowerCase();
                            }
                        }
                    }
                }
                //
                class enableStudentTestScoreControl implements ItemListener {
                    
                    public void itemStateChanged(ItemEvent e) {
                        if(displayStudentTestScoreCheckBox.isSelected()) {
                            getDisplayStudentTestScoreCheckBox = "yes";
                        }
                        else {
                            getDisplayStudentTestScoreCheckBox = "no";
                        }
                    }
                }
                //
                class questionModeControl implements ActionListener {

                    public void actionPerformed(ActionEvent e) {
                        for (int x = 0; x < questionParaModeList.length; x++) {
                            if (x == questionParaModeBox.getSelectedIndex()) {
                                getQuestionMode = questionParaModeList[x].toLowerCase();
                            }
                        }
                        //
                        int ct = 0;
                        if(getQuestionMode.equalsIgnoreCase("objective")) {
                            ct = getNumberOfDetails("select distinct questionbatchnumber "
                                        + "from questionprofile");
                            if (ct > 0) {
                                questionBatchParaList = 
                                        populateList("select distinct questionbatchnumber "
                                        + "from questionprofile "
                                        + "order by questionbatchnumber", ct);
                                //
                                questionBatchParaBox.removeAllItems();
                                for (int pos = 0; pos < questionBatchParaList.length; pos++) {
                                    questionBatchParaBox.addItem(
                                            questionBatchParaList[pos].toString());
                                }
                            }
                        }
                        else if(getQuestionMode.equalsIgnoreCase("theory")) {
                            ct = getNumberOfDetails("select distinct questionbatchnumber "
                                        + "from theoryquestionprofile");
                            if (ct > 0) {
                                questionBatchParaList = 
                                        populateList("select distinct questionbatchnumber "
                                        + "from theoryquestionprofile "
                                        + "order by questionbatchnumber", ct);
                                //
                                questionBatchParaBox.removeAllItems();
                                for (int pos = 0; pos < questionBatchParaList.length; pos++) {
                                    questionBatchParaBox.addItem(
                                            questionBatchParaList[pos].toString());
                                }
                            }
                        }
                        //
                        getQuestionBatch = "";
                    }
                }
                //
                class submitTestInfoControl implements ActionListener {

                    public void actionPerformed(ActionEvent e) {
                        //
                        int questionInBank = 0;
                        if(getQuestionMode.equalsIgnoreCase("objective")) {
                            questionInBank = getNumberOfDetails("select * from questionprofile "
                                + "where questionclass = '" + getTestClass + "' "
                                + "and questionterm = '" + getTestTerm + "' "
                                + "and questionsession = '" + getTestSession + "' "
                                + "and questionsubject = '" + getTestSubject + "' "
                                + "and questionbatchnumber = '" + getQuestionBatch + "' "
                                + "and questionmode = '" + getQuestionMode + "'");
                            //
                            if(getTestClass.equalsIgnoreCase("") ||
                                getTestTerm.equalsIgnoreCase("") ||
                                getTestSession.equalsIgnoreCase("") ||
                                getTestSubject.equalsIgnoreCase("") ||
                                getQuestionBatch.equalsIgnoreCase("") ||
                                getQuestionMode.equalsIgnoreCase("") ||
                                scorePerQuestionField.getText().equalsIgnoreCase("") ||
                                numberOfQuestionField.getText().equalsIgnoreCase("") ||
                                questionTestMinuteDurationField.getText().equalsIgnoreCase("")) {
                                //
                                promptLab.setText("Empty field Detected");
                                JOptionPane.showMessageDialog(null, 
                                        promptLab,
                                        getSchoolName.toUpperCase(),
                                        JOptionPane.INFORMATION_MESSAGE,
                                        infoIcon);
                            }
                            else if(Integer.parseInt(numberOfQuestionField.getText()) > 
                                    questionInBank) {
                                //
                                promptLab.setText("Number Of Questions Entered Is Greater Than "
                                        + "Available Questions In Bank Using Parameter.");
                                JOptionPane.showMessageDialog(null, 
                                        promptLab,
                                        getSchoolName.toUpperCase(),
                                        JOptionPane.WARNING_MESSAGE,
                                        warningIcon);
                                //
                                promptLab.setText("Available Bank From Selected Parameter: " + 
                                        questionInBank + " Questions");
                                JOptionPane.showMessageDialog(null, 
                                        promptLab,
                                        getSchoolName.toUpperCase(),
                                        JOptionPane.INFORMATION_MESSAGE,
                                        infoIcon);
                                //
                                numberOfQuestionField.setText("");
                            }
                            else if(getNumberOfDetails("select * from loadtestprofile "
                                    + "where questionbatchnumber = '" + getQuestionBatch + "' "
                                    + "and setupclass = '" + getTestClass.toLowerCase() + "' "
                                    + "and setupterm = '" + getTestTerm.toLowerCase() + "' "
                                    + "and setupsubject = '" + getTestSubject.toLowerCase() + "' "
                                    + "and setupsession = '" + getTestSession + "' "
                                    + "and questionmode = '" + getQuestionMode + "'") > 0) {
                                //
                                promptLab.setText("Test Schedule With Specification Already Exist. "
                                        + "Modify as Appropriate.");
                                JOptionPane.showMessageDialog(null, 
                                        promptLab,
                                        getSchoolName.toUpperCase(),
                                        JOptionPane.INFORMATION_MESSAGE,
                                        infoIcon);
                            }
                            else {
                                int counter = 
                                        (getNumberOfDetails("select * from loadtestprofile "
                                        + "where setupterm = '" + getTestTerm.toLowerCase() + "' "
                                        + "and setupsession = '" + getTestSession + "'") + 1);
                                //
                                insertUpdateDelete("insert into loadtestprofile "
                                        + "values ('" + counter + "', "
                                                + "'" + getTestClass.toLowerCase() + "', "
                                                + "'" + getTestTerm.toLowerCase() + "', "
                                                + "'" + getTestSession + "', "
                                                + "'" + getTestSubject.toLowerCase() + "', "
                                                + "'" + getQuestionMode.toLowerCase() + "', "
                                                + "'" + scorePerQuestionField.getText() + "', "
                                                + "'" + getQuestionBatch + "', "
                                                + "'" + numberOfQuestionField.getText() + "', "
                                                + "'" + questionTestMinuteDurationField.getText() + "', "
                                                + "'" + getDisplayStudentTestScoreCheckBox + "')");
                                //
                                if(getNumberOfDetails("select * from stoptestprofile "
                                        + "where setupterm = '" + getTestTerm + "' "
                                        + "and setupclass = '" + getTestClass + "' "
                                        + "and setupsession = '" + getTestSession + "' "
                                        + "and setupsubject = '" + getTestSubject + "' "
                                        + "and questionmode = '" + getQuestionMode + "'") > 0) {
                                    //
                                    insertUpdateDelete("delete stoptestprofile "
                                        + "where setupterm = '" + getTestTerm + "' "
                                        + "and setupclass = '" + getTestClass + "' "
                                        + "and setupsession = '" + getTestSession + "' "
                                        + "and setupsubject = '" + getTestSubject + "' "
                                        + "and questionmode = '" + getQuestionMode + "'");
                                }
                                //
                                insertUpdateDelete("insert into stoptestprofile "
                                    + "values ('" + getTestClass.toLowerCase() + "', "
                                            + "'" + getTestTerm.toLowerCase() + "', "
                                            + "'" + getTestSession + "', "
                                            + "'" + getTestSubject.toLowerCase() + "', "
                                            + "'" + getQuestionMode.toLowerCase() + "', "
                                            + "'no', "
                                            + "'nil', "
                                            + "'" + getTestScheduleAS + "')");
                                //
                                promptLab.setText("Load Test Schedule Details Saved.");
                                JOptionPane.showMessageDialog(null, 
                                        promptLab,
                                        getSchoolName.toUpperCase(),
                                        JOptionPane.INFORMATION_MESSAGE,
                                        infoIcon);
                                //
                                new viewTestSetup(getTestTerm, getTestSession, 
                                        getQuestionMode, getTestClass, 
                                        getTestSubject).start();
                            }
                        }
                        else if(getQuestionMode.equalsIgnoreCase("theory")) {
                            questionInBank = getNumberOfDetails("select * from theoryquestionprofile "
                                + "where questionclass = '" + getTestClass + "' "
                                + "and questionterm = '" + getTestTerm + "' "
                                + "and questionsession = '" + getTestSession + "' "
                                + "and questionsubject = '" + getTestSubject + "' "
                                + "and questionbatchnumber = '" + getQuestionBatch + "' "
                                + "and questionmode = '" + getQuestionMode + "'");
                            //
                            if(getTestClass.equalsIgnoreCase("") ||
                                getTestTerm.equalsIgnoreCase("") ||
                                getTestSession.equalsIgnoreCase("") ||
                                getTestSubject.equalsIgnoreCase("") ||
                                getQuestionBatch.equalsIgnoreCase("") ||
                                getQuestionMode.equalsIgnoreCase("") ||
                                scorePerQuestionField.getText().equalsIgnoreCase("") ||
                                numberOfQuestionField.getText().equalsIgnoreCase("") ||
                                questionTestMinuteDurationField.getText().equalsIgnoreCase("")) {
                                //
                                promptLab.setText("Empty field Detected");
                                JOptionPane.showMessageDialog(null, 
                                        promptLab,
                                        getSchoolName.toUpperCase(),
                                        JOptionPane.INFORMATION_MESSAGE,
                                        infoIcon);
                            }
                            else if(Integer.parseInt(numberOfQuestionField.getText()) > 
                                    questionInBank) {
                                //
                                promptLab.setText("Number Of Questions Entered Is Greater Than "
                                        + "Available Questions In Bank Using Parameter.");
                                JOptionPane.showMessageDialog(null, 
                                        promptLab,
                                        getSchoolName.toUpperCase(),
                                        JOptionPane.WARNING_MESSAGE,
                                        warningIcon);
                                //
                                promptLab.setText("Available Bank From Selected Parameter: " + 
                                        questionInBank + " Questions");
                                JOptionPane.showMessageDialog(null, 
                                        promptLab,
                                        getSchoolName.toUpperCase(),
                                        JOptionPane.INFORMATION_MESSAGE,
                                        infoIcon);
                                //
                                numberOfQuestionField.setText("");
                            }
                            else if(getNumberOfDetails("select * from loadtestprofile "
                                    + "where questionbatchnumber = '" + getQuestionBatch + "' "
                                    + "and setupclass = '" + getTestClass.toLowerCase() + "' "
                                    + "and setupterm = '" + getTestTerm.toLowerCase() + "' "
                                    + "and setupsubject = '" + getTestSubject.toLowerCase() + "' "
                                    + "and setupsession = '" + getTestSession + "' "
                                    + "and questionmode = '" + getQuestionMode + "'") > 0) {
                                //
                                promptLab.setText("Test Schedule With Specification Already Exist. "
                                        + "Modify as Appropriate.");
                                JOptionPane.showMessageDialog(null, 
                                        promptLab,
                                        getSchoolName.toUpperCase(),
                                        JOptionPane.INFORMATION_MESSAGE,
                                        infoIcon);
                            }
                            else {
                                int counter = 
                                        (getNumberOfDetails("select * from loadtestprofile "
                                        + "where setupterm = '" + getTestTerm.toLowerCase() + "' "
                                        + "and setupsession = '" + getTestSession + "'") + 1);
                                //
                                insertUpdateDelete("insert into loadtestprofile "
                                        + "values ('" + counter + "', "
                                                + "'" + getTestClass.toLowerCase() + "', "
                                                + "'" + getTestTerm.toLowerCase() + "', "
                                                + "'" + getTestSession + "', "
                                                + "'" + getTestSubject.toLowerCase() + "', "
                                                + "'" + getQuestionMode.toLowerCase() + "', "
                                                + "'" + scorePerQuestionField.getText() + "', "
                                                + "'" + getQuestionBatch + "', "
                                                + "'" + numberOfQuestionField.getText() + "', "
                                                + "'" + questionTestMinuteDurationField.getText() + "')");
                                //
                                promptLab.setText("Load Test Schedule Details Saved.");
                                JOptionPane.showMessageDialog(null, 
                                        promptLab,
                                        getSchoolName.toUpperCase(),
                                        JOptionPane.INFORMATION_MESSAGE,
                                        infoIcon);
                                //
                                new viewTestSetup(getTestTerm, getTestSession, 
                                        getQuestionMode, getTestClass, 
                                        getTestSubject).start();
                            }
                        }
                        //
                        getQuestionMode = "";
                        getTestTerm = "";
                        getTestClass = "";
                        getTestSession = "";
                        getTestSubject = "";
                        getQuestionBatch = "";
                        scorePerQuestionField.setText("");
                        numberOfQuestionField.setText("");
                        questionTestMinuteDurationField.setText("");
                    }
                }
                //
                class selectClassControl implements ActionListener {

                    public void actionPerformed(ActionEvent e) {
                        for (int x = 0; x < classParaList.length; x++) {
                            if (x == classParaBox.getSelectedIndex()) {
                                getTestClass = classParaList[x].toLowerCase();
                            }
                        }
                    }
                }
                //
                class selectQuestionBatchControl implements ActionListener {

                    public void actionPerformed(ActionEvent e) {
                        for (int x = 0; x < questionBatchParaList.length; x++) {
                            if (x == questionBatchParaBox.getSelectedIndex()) {
                                getQuestionBatch = questionBatchParaList[x];
                            }
                        }
                    }
                }
                //
                class selectSubjectControl implements ActionListener {

                    public void actionPerformed(ActionEvent e) {
                        for (int x = 0; x < subjectParaList.length; x++) {
                            if (x == subjectParaBox.getSelectedIndex()) {
                                getTestSubject = subjectParaList[x].toLowerCase();
                            }
                        }
                    }
                }
                //
                class selectSessionControl implements ActionListener {

                    public void actionPerformed(ActionEvent e) {
                        for (int x = 0; x < sessionParaList.length; x++) {
                            if (x == sessionParaBox.getSelectedIndex()) {
                                getTestSession = sessionParaList[x];
                            }
                        }
                    }
                }
                //
                class selectTermControl implements ActionListener {

                    public void actionPerformed(ActionEvent e) {
                        for (int x = 0; x < termParaList.length; x++) {
                            if (x == termParaBox.getSelectedIndex()) {
                                getTestTerm = termParaList[x].toLowerCase();
                            }
                        }
                    }
                }
            }
            //
            class viewScoreReport extends Thread {
                String testClass = "",
                        testTerm = "",
                        testSubject = "",
                        testYear = "",
                        testMode = "",
                        getViewOpt = "",
                        //
                        studentID = "";
                //
                String[] resList = null;
                //
                public viewScoreReport(String cl, String tm,
                        String yr, String sb, String opt, 
                        String mode, String id) {
                    //
                    studentID = id;
                    testClass = cl;
                    testTerm = tm;
                    testSubject = sb;
                    testYear = yr;
                    testMode = mode;
                    getViewOpt = opt;
                }
                //
                public void run() {
                    JPanel subjectBoxPanel = new JPanel();
                    subjectBoxPanel.setBackground(themeColor);
                    //
                    if (!testYear.equalsIgnoreCase("")
                            && !testClass.equalsIgnoreCase("")
                            && !testMode.equalsIgnoreCase("")
                            && !testTerm.equalsIgnoreCase("")
                            && !testSubject.equalsIgnoreCase("")) {
                        //
                        if(!studentID.equalsIgnoreCase("")) {
                            resCount = getNumberOfDetails("select * from testscoresprofile "
                                + "where testsession = '" + testYear + "' "
                                + "and testterm = '" + testTerm + "' "
                                + "and testclass = '" + testClass + "' "
                                + "and questionmode = '" + testMode + "' "
                                + "and testsubject = '" + testSubject + "' "
                                + "and registrationnumber = '" + studentID + "'");
                        }
                        else {
                            resCount = getNumberOfDetails("select * from testscoresprofile "
                                + "where testsession = '" + testYear + "' "
                                + "and testterm = '" + testTerm + "' "
                                + "and testclass = '" + testClass + "' "
                                + "and questionmode = '" + testMode + "' "
                                + "and testsubject = '" + testSubject + "'");
                        }
                    } 
                    //
                    if (resCount > 0) {
                        //
                        testingDisplayPanel.removeAll();
                        testingDisplayPanel.add(new JLabel(
                                        new ImageIcon("images/loadicon.gif")));
                        //
                        if(getViewOpt.equalsIgnoreCase("report")) {
                            resCount = getNumberOfDetails("select distinct registrationnumber "
                                + "from testscoresprofile "
                                + "where testsession = '" + testYear + "' "
                                + "and testclass = '" + testClass + "' "
                                + "and questionmode = '" + testMode + "' "
                                + "and testterm = '" + testTerm + "' "
                                + "and testsubject = '" + testSubject + "'");
                            //
                            resList = populateList("select distinct registrationnumber "
                                    + "from testscoresprofile "
                                    + "where testsession = '" + testYear + "' "
                                    + "and testterm = '" + testTerm + "' "
                                    + "and testclass = '" + testClass + "' "
                                    + "and questionmode = '" + testMode + "' "
                                    + "and testsubject = '" + testSubject + "' "
                                    + "order by registrationnumber", resCount);
                            //
                            int subjCt = getNumberOfDetails("select distinct testsubject "
                                    + "from testscoresprofile "
                                    + "where testsession = '" + testYear + "' "
                                    + "and questionmode = '" + testMode + "' "
                                    + "and testterm = '" + testTerm + "' "
                                    + "and testclass = '" + testClass + "'");
                            //
                            String[] subjList = populateList("select distinct testsubject "
                                    + "from testscoresprofile "
                                    + "where testsession = '" + testYear + "' "
                                    + "and testterm = '" + testTerm + "' "
                                    + "and questionmode = '" + testMode + "' "
                                    + "and testclass = '" + testClass + "' "
                                    + "order by testsubject", subjCt);
                            //
                            subjectBoxPanel.setLayout(
                                    new GridLayout(resCount + 1, subjCt + 1, 0, 0));
                            //
                            printExcelText = "";
                            JTextArea[] genericViewHeaderField = new JTextArea[subjCt + 1];
                            //student names
                            genericViewHeaderField[0] = new JTextArea("REGISTRATION NUMBER");
                            genericViewHeaderField[0].setForeground(Color.BLUE);
                            genericViewHeaderField[0].setFont(new Font("canbria", Font.BOLD, 10));
                            genericViewHeaderField[0].setBorder(BorderFactory.createEtchedBorder());
                            genericViewHeaderField[0].setEditable(false);
                            genericViewHeaderField[0].setLineWrap(true);
                            genericViewHeaderField[0].setWrapStyleWord(true);
                            //
                            printExcelText += genericViewHeaderField[0].getText();
                            subjectBoxPanel.add(genericViewHeaderField[0]);
                            //
                            for (int pos = 0; pos < subjCt; pos++) {
                                genericViewHeaderField[pos + 1] = new JTextArea(
                                        subjList[pos].toUpperCase(), 2, 5);
                                genericViewHeaderField[pos + 1].setForeground(Color.BLUE);
                                genericViewHeaderField[pos + 1].setFont(new Font("canbria", Font.BOLD, 10));
                                genericViewHeaderField[pos + 1].setBorder(BorderFactory.createEtchedBorder());
                                genericViewHeaderField[pos + 1].setEditable(false);
                                genericViewHeaderField[pos + 1].setLineWrap(true);
                                genericViewHeaderField[pos + 1].setWrapStyleWord(true);
                                //
                                printExcelText += genericViewHeaderField[pos + 1].getText();
                                subjectBoxPanel.add(genericViewHeaderField[pos + 1]);
                            }
                            //
                            JTextArea[][] viewContentField = 
                                    new JTextArea[resCount][subjCt + 1];
                            //
                            for(int row = 0; row < resCount; row++) {
                                viewContentField[row][0] = new JTextArea(
                                        resList[row].toUpperCase(), 2, 5);
                                viewContentField[row][0].setForeground(Color.BLACK);
                                viewContentField[row][0].setFont(new Font("canbria", Font.PLAIN, 12));
                                viewContentField[row][0].setBorder(BorderFactory.createEmptyBorder());
                                viewContentField[row][0].setEditable(false);
                                viewContentField[row][0].setLineWrap(true);
                                viewContentField[row][0].setWrapStyleWord(true);
                                //
                                if (Math.IEEEremainder(row, 2) == 0) {
                                    viewContentField[row][0].setBackground(themeColor);
                                } 
                                else {
                                    viewContentField[row][0].setBackground(Color.LIGHT_GRAY);
                                }
                                //
                                printExcelText += viewContentField[row][0].getText();
                                subjectBoxPanel.add(viewContentField[row][0]);
                                //
                                commTestLab.setText(" Uploading " + (row + 1) + " Records From "
                                        + "Selected Parameters ...Please Wait!");
                                //
                                for(int col = 0; col < subjCt; col++) {
                                    String getSc = 
                                            getOneDetail("select testscore from testscoresprofile "
                                            + "where registrationnumber = '" + resList[row] + "' "
                                            + "and testsubject = '" + subjList[col] + "' "
                                            + "and testsession = '" + testYear + "' "
                                            + "and questionmode = '" + testMode + "' "
                                            + "and testterm = '" + testTerm + "' "
                                            + "and testclass = '" + testClass + "'");
                                    //
                                    viewContentField[row][col + 1] = new JTextArea(
                                            (getSc.equalsIgnoreCase("") ? "-" : getSc), 2, 5);
                                    //
                                    viewContentField[row][col + 1].setForeground(Color.BLACK);
                                    viewContentField[row][col + 1].setFont(new Font("canbria", Font.PLAIN, 12));
                                    viewContentField[row][col + 1].setBorder(BorderFactory.createEmptyBorder());
                                    viewContentField[row][col + 1].setEditable(false);
                                    viewContentField[row][col + 1].setLineWrap(true);
                                    viewContentField[row][col + 1].setWrapStyleWord(true);
                                    //
                                    try{sleep(80);}catch(InterruptedException ererer){}
                                    //
                                    if (Math.IEEEremainder(row, 2) == 0) {
                                        viewContentField[row][col + 1].setBackground(themeColor);
                                    } else {
                                        viewContentField[row][col + 1].setBackground(Color.LIGHT_GRAY);
                                    }
                                    //
                                    printExcelText += viewContentField[row][col + 1].getText();
                                    subjectBoxPanel.add(viewContentField[row][col + 1]);
                                    //
                                    try{sleep(200);}catch(InterruptedException erer){}
                                }
                            }
                            //
                            commTestLab.setText(" Uploaded " + resCount + " Records From "
                                        + "Selected Parameters.");
                        }
                        //
                        if(getViewOpt.equalsIgnoreCase("score")) {
                            //
                            if (!testYear.equalsIgnoreCase("")
                                    && !testClass.equalsIgnoreCase("")
                                    && !testTerm.equalsIgnoreCase("")
                                    && !testMode.equalsIgnoreCase("")
                                    && !testSubject.equalsIgnoreCase("")) {
                                //
                                if(!studentID.equalsIgnoreCase("")) {
                                    resList = populateList("select counter from testscoresprofile "
                                            + "where testsession = '" + testYear + "' "
                                            + "and testterm = '" + testTerm + "' "
                                            + "and testclass = '" + testClass + "' "
                                            + "and questionmode = '" + testMode + "' "
                                            + "and testsubject = '" + testSubject + "' "
                                            + "and registrationnumber = '" + studentID + "' "
                                            + "order by registrationnumber, testsubject", resCount);
                                }
                                else {
                                    resList = populateList("select counter from testscoresprofile "
                                            + "where testsession = '" + testYear + "' "
                                            + "and testterm = '" + testTerm + "' "
                                            + "and testclass = '" + testClass + "' "
                                            + "and questionmode = '" + testMode + "' "
                                            + "and testsubject = '" + testSubject + "' "
                                            + "order by registrationnumber, testsubject", resCount);
                                }
                            } 
                            //
                            studentID = "";
                            //
                            subjectBoxPanel.setLayout(new GridLayout(resCount + 1, 1, 0, 0));
                            //
                            printExcelText = "";
                            //
                            JPanel headerPanel = new JPanel();
                            headerPanel.setBackground(Color.LIGHT_GRAY);
                            headerPanel.add(new JLabel("          "));
                            //
                            JTextField nameLab = new JTextField("REGISTRATION NUMBER", 21);
                            nameLab.setForeground(Color.BLUE);
                            nameLab.setFont(new Font("canbria", Font.BOLD, 14));
                            nameLab.setBorder(BorderFactory.createEmptyBorder());
                            nameLab.setBackground(Color.LIGHT_GRAY);
                            nameLab.setEditable(false);
                            //
                            printExcelText += nameLab.getText();
                            //
                            JTextField subLab = new JTextField("SUBJECT", 11);
                            subLab.setForeground(Color.BLUE);
                            subLab.setFont(new Font("canbria", Font.BOLD, 14));
                            subLab.setBorder(BorderFactory.createEmptyBorder());
                            subLab.setBackground(Color.LIGHT_GRAY);
                            subLab.setEditable(false);
                            //
                            printExcelText += subLab.getText();
                            //
                            JTextField totalLab = new JTextField("SCORE", 7);
                            totalLab.setForeground(Color.BLUE);
                            totalLab.setFont(new Font("canbria", Font.BOLD, 14));
                            totalLab.setBorder(BorderFactory.createEmptyBorder());
                            totalLab.setBackground(Color.LIGHT_GRAY);
                            //
                            printExcelText += totalLab.getText();
                            //
                            JTextField gradeLab = new JTextField("ANSWERED", 8);
                            gradeLab.setForeground(Color.BLUE);
                            gradeLab.setFont(new Font("canbria", Font.BOLD, 14));
                            gradeLab.setBorder(BorderFactory.createEmptyBorder());
                            gradeLab.setBackground(Color.LIGHT_GRAY);
                            //
                            printExcelText += gradeLab.getText();
                            //
                            JTextField dateLab = new JTextField("TEST DATE");
                            dateLab.setForeground(Color.BLUE);
                            dateLab.setFont(new Font("canbria", Font.BOLD, 14));
                            dateLab.setBorder(BorderFactory.createEmptyBorder());
                            dateLab.setBackground(Color.LIGHT_GRAY);
                            //
                            headerPanel.add(nameLab);
                            headerPanel.add(subLab);
                            headerPanel.add(totalLab);
                            headerPanel.add(gradeLab);
                            headerPanel.add(dateLab);
                            //
                            printExcelText += dateLab.getText();
                            //
                            JPanel headerLeftPanel = new JPanel();
                            headerLeftPanel.setBackground(Color.LIGHT_GRAY);
                            headerLeftPanel.setLayout(new BorderLayout());
                            headerLeftPanel.add(headerPanel, BorderLayout.WEST);
                            //
                            subjectBoxPanel.add(headerLeftPanel);
                            //
                            testClassField = new JTextField[resCount];
                            testTermField = new JTextField[resCount];
                            testSessionField = new JTextField[resCount];
                            testSubjectField = new JTextField[resCount];
                            numberOfTestQuestionField = new JTextField[resCount];
                            testDurationField = new JTextField[resCount];
                            testQuestionBatchField = new JTextField[resCount];
                            testSetupButton = new JButton[resCount];
                            testDeleteButton = new JButton[resCount];
                            testCheckBox = new JCheckBox[resCount];
                            //
                            for (int pos = 0; pos < resCount; pos++) {
                                JPanel onePanel = new JPanel();
                                //
                                commTestLab.setText(" Uploading " + (pos + 1) + " Records From "
                                        + "Selected Parameters ...Please Wait!");
                                //
                                testCheckBox[pos] = new JCheckBox();
                                testCheckBox[pos].setBackground(themeColor);
                                testCheckBox[pos].addItemListener(new enableField(pos));
                                onePanel.add(testCheckBox[pos]);
                                //
                                onePanel.add(new JLabel("  "));
                                //
                                testTermField[pos] = new JTextField(
                                        getOneDetail("select registrationnumber from testscoresprofile "
                                        + "where counter = '" + resList[pos] + "'"), 23);
                                testTermField[pos].setForeground(Color.BLACK);
                                testTermField[pos].setBorder(BorderFactory.createEmptyBorder());
                                testTermField[pos].setFont(new Font("canbria", Font.PLAIN, 12));
                                testTermField[pos].setEditable(false);
                                //
                                if(getQuestionMode.equalsIgnoreCase("theory")) {
                                    testTermField[pos].setCursor(
                                            Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                                    testTermField[pos].setToolTipText("Click For Theory Summary");
                                    testTermField[pos].addMouseListener(
                                            new previewStudentTheorySummary(pos));
                                }
                                //
                                onePanel.add(testTermField[pos]);
                                //
                                printExcelText += testTermField[pos].getText().toUpperCase() + "\t";
                                //
                                testClassField[pos] = new JTextField(
                                        getOneDetail("select testsubject from testscoresprofile "
                                        + "where counter = '" + resList[pos] + "'").toUpperCase(), 12);
                                testClassField[pos].setForeground(Color.BLACK);
                                testClassField[pos].setBorder(BorderFactory.createEmptyBorder());
                                testClassField[pos].setFont(new Font("canbria", Font.PLAIN, 12));
                                testClassField[pos].setEditable(false);
                                testClassField[pos].setToolTipText(
                                        testClassField[pos].getText());
                                onePanel.add(testClassField[pos]);
                                //
                                printExcelText += 
                                        testClassField[pos].getText().toUpperCase() + "\t";
                                //
                                testSubjectField[pos] = new JTextField(
                                        getOneDetail("select testscore from testscoresprofile "
                                        + "where counter = '" + resList[pos] + "'"), 8);
                                testSubjectField[pos].setForeground(Color.BLACK);
                                testSubjectField[pos].setBorder(BorderFactory.createEmptyBorder());
                                testSubjectField[pos].setFont(new Font("canbria", Font.PLAIN, 12));
                                testSubjectField[pos].setEditable(false);
                                onePanel.add(testSubjectField[pos]);
                                //
                                printExcelText += 
                                            testSubjectField[pos].getText().toUpperCase() + "\t";
                                //
                                testSessionField[pos] = new JTextField(
                                        getOneDetail("select questionanswered from testscoresprofile "
                                        + "where counter = '" + resList[pos] + "'"), 8);
                                testSessionField[pos].setForeground(Color.BLACK);
                                testSessionField[pos].setBorder(BorderFactory.createEmptyBorder());
                                testSessionField[pos].setFont(new Font("canbria", Font.PLAIN, 12));
                                testSessionField[pos].setEditable(false);
                                onePanel.add(testSessionField[pos]);
                                //
                                printExcelText += 
                                            testSessionField[pos].getText().toUpperCase() + "\t";
                                //
                                testDurationField[pos] = new JTextField(
                                        getOneDetail("select datewritten from testscoresprofile "
                                        + "where counter = '" + resList[pos] + "'").
                                        toUpperCase(), 12);
                                testDurationField[pos].setForeground(Color.BLACK);
                                testDurationField[pos].setBorder(BorderFactory.createEmptyBorder());
                                testDurationField[pos].setFont(new Font("canbria", Font.PLAIN, 12));
                                testDurationField[pos].setEditable(false);
                                onePanel.add(testDurationField[pos]);
                                //
                                printExcelText += 
                                        testDurationField[pos].getText().toUpperCase() + "\t";
                                //
                                try{sleep(80);}catch(InterruptedException ererer){}
                                //
                                testSetupButton[pos] = new JButton("Update");
                                testSetupButton[pos].setForeground(Color.BLUE);
                                testSetupButton[pos].setBackground(themeColor);
                                testSetupButton[pos].setFont(new Font("canbria", Font.BOLD, 10));
                                testSetupButton[pos].addActionListener(new saveUpdate(pos));
                                //
                                testDeleteButton[pos] = new JButton("Remove");
                                testDeleteButton[pos].setForeground(Color.RED);
                                testDeleteButton[pos].setBackground(themeColor);
                                testDeleteButton[pos].setFont(new Font("canbria", Font.BOLD, 10));
                                testDeleteButton[pos].addActionListener(new saveDelete(pos));
                                //
                                onePanel.add(new JLabel("  "));
                                onePanel.add(testSetupButton[pos]);
                                onePanel.add(new JLabel("  "));
                                onePanel.add(testDeleteButton[pos]);
                                //
                                if (Math.IEEEremainder(pos, 2) == 0) {
                                    onePanel.setBackground(themeColor);
                                    testTermField[pos].setBackground(themeColor);
                                    testClassField[pos].setBackground(themeColor);
                                    testSubjectField[pos].setBackground(themeColor);
                                    testSessionField[pos].setBackground(themeColor);
                                    testCheckBox[pos].setBackground(themeColor);
                                    testDurationField[pos].setBackground(themeColor);
                                } 
                                else {
                                    onePanel.setBackground(Color.LIGHT_GRAY);
                                    testTermField[pos].setBackground(Color.LIGHT_GRAY);
                                    testClassField[pos].setBackground(Color.LIGHT_GRAY);
                                    testSubjectField[pos].setBackground(Color.LIGHT_GRAY);
                                    testSessionField[pos].setBackground(Color.LIGHT_GRAY);
                                    testCheckBox[pos].setBackground(Color.LIGHT_GRAY);
                                    testDurationField[pos].setBackground(Color.LIGHT_GRAY);
                                }
                                //
                                subjectBoxPanel.add(onePanel);
                                try{sleep(100);}catch(InterruptedException erer){}
                            }
                            //
                            commTestLab.setText(" Uploaded " + resCount + " Records From "
                                        + "Selected Parameters.");
                        }
                    } 
                    else {
                        subjectBoxPanel.removeAll();
                        subjectBoxPanel.add(new JLabel("No Scores Or "
                                + "Report Details Yet!"));
                    }
                    //
                    subjectBoxPanel.setBorder(BorderFactory.createEtchedBorder());
                    //
                    JPanel cenSubjectBoxPanel = new JPanel();
                    cenSubjectBoxPanel.setBackground(themeColor);
                    cenSubjectBoxPanel.add(subjectBoxPanel);
                    //
                    testingDisplayPanel.removeAll();
                    testingDisplayPanel.add(cenSubjectBoxPanel, BorderLayout.WEST);
                    validate();
                }
                //
                class previewStudentTheorySummary extends MouseAdapter {
                    int pos = 0;
                    public previewStudentTheorySummary(int  p) {pos = p;}
                    public void mouseClicked(MouseEvent e) {
                        studentTheorySummaryDialog theoryDialog = 
                                new studentTheorySummaryDialog(
                                testTermField[pos].getText());
                        theoryDialog.setTitle(testTermField[pos].getText().
                                toUpperCase() + " Theory Assessment");
                        theoryDialog.setLocation(0, 0);
                        Dimension frame = resWizard.getToolkit().getScreenSize();
                        theoryDialog.setSize(650, frame.height - 30);
                        theoryDialog.setVisible(true);
                        theoryDialog.setDefaultCloseOperation(
                                theoryDialog.HIDE_ON_CLOSE);
                    }
                }
                //
                class studentTheorySummaryDialog extends JDialog {
                    String studName = "";
                    //
                    JLabel updateScoreLab = new JLabel("Update Theory Score: ", JLabel.RIGHT),
                            studentNameLab = new JLabel("");
                    JTextField updateScoreField = new JTextField(7);
                    JButton saveButton = new JButton("SAVE");
                    //
                    JPanel previewPanel = new JPanel(),
                            updateButtonPanel = new JPanel();
                    //
                    JPanel[] eachPanel = null;
                    JTextArea[] questionArea = null,
                                answerArea = null;
                    //
                    public studentTheorySummaryDialog(String n) {
                        studName = n;
                        studentNameLab.setText(studName.toUpperCase() + " Test Theory");
                        studentNameLab.setFont(new Font(getFontType, Font.BOLD, 16));
                        studentNameLab.setForeground(Color.BLUE);
                        studentNameLab.setBorder(BorderFactory.createEtchedBorder());
                        //
                        int questCount = getNumberOfDetails("select * "
                                + "from studenttheoryanswerprofile "
                                + "where testclass = '" + getTestClass.toLowerCase() + "' "
                                + "and testterm = '" + getTestTerm.toLowerCase() + "' "
                                + "and testsession = '" + getTestSession + "' "
                                + "and questionmode = '" + getQuestionMode + "' "
                                + "and testsubject = '" + getTestSubject.toLowerCase() + "' "
                                + "and registrationnumber = '" + studName + "'");
                        //
                        eachPanel = new JPanel[questCount];
                        questionArea = new JTextArea[questCount];
                        answerArea = new JTextArea[questCount];
                        //
                        String[] questList = populateList("select counter "
                                + "from studenttheoryanswerprofile "
                                + "where testclass = '" + getTestClass.toLowerCase() + "' "
                                + "and testterm = '" + getTestTerm.toLowerCase() + "' "
                                + "and testsession = '" + getTestSession + "' "
                                + "and questionmode = '" + getQuestionMode + "' "
                                + "and testsubject = '" + getTestSubject.toLowerCase() + "' "
                                + "and registrationnumber = '" + studName + "'",
                                questCount);
                        //
                        JPanel centPreviewPanel = new JPanel();
                        centPreviewPanel.setLayout(new GridLayout(questCount, 1, 0, 2));
                        centPreviewPanel.setBackground(Color.WHITE);
                        //
                        for(int col = 0; col < questCount; col++) {
                            eachPanel[col] = new JPanel();
                            eachPanel[col].setLayout(new GridLayout(2, 1, 0, 2));
                            eachPanel[col].setBackground(Color.WHITE);
                            //
                            String qText = getOneDetail("select question "
                                    + "from studenttheoryanswerprofile "
                                    + "where counter = '" + questList[col] + "'");
                            //
                            if(qText.startsWith("//" + getServerIP + "/")) {
                                eachPanel[col].add(new JLabel(new ImageIcon(qText)));
                                eachPanel[col].setBorder(
                                            BorderFactory.createTitledBorder(null,
                                            " Question " + (col + 1),
                                            TitledBorder.LEFT, 0, 
                                            new Font(getFontType, Font.BOLD, 10), 
                                            Color.BLUE));
                            }
                            else {
                                questionArea[col] = new JTextArea(qText, 13, 48);
                                questionArea[col].setFont(new Font(getFontType, Font.PLAIN, 14));
                                questionArea[col].setEditable(false);
                                questionArea[col].setBorder(BorderFactory.createEtchedBorder());
                                questionArea[col].setLineWrap(true);
                                questionArea[col].setWrapStyleWord(true);
                                questionArea[col].setBorder(
                                            BorderFactory.createTitledBorder(null,
                                            " Question " + (col + 1),
                                            TitledBorder.LEFT, 0, 
                                            new Font(getFontType, Font.BOLD, 10), 
                                            Color.BLUE));
                                //
                                eachPanel[col].add(questionArea[col]);
                            }
                            //
                            answerArea[col] = new JTextArea(
                                    getOneDetail("select answered from studenttheoryanswerprofile "
                                    + "where counter = '" + questList[col] + "'"), 13, 48);
                            answerArea[col].setFont(new Font(getFontType, Font.PLAIN, 14));
                            answerArea[col].setEditable(false);
                            answerArea[col].setBorder(BorderFactory.createEtchedBorder());
                            answerArea[col].setLineWrap(true);
                            answerArea[col].setWrapStyleWord(true);
                            answerArea[col].setBorder(
                                            BorderFactory.createTitledBorder(null,
                                            " Answer " + (col + 1),
                                            TitledBorder.LEFT, 0, 
                                            new Font(getFontType, Font.BOLD, 10), 
                                            Color.BLUE));
                            //
                            eachPanel[col].add(answerArea[col]);
                            //
                            centPreviewPanel.add(eachPanel[col]);
                        }
                        //
                        previewPanel.setBackground(Color.WHITE);
                        previewPanel.add(centPreviewPanel);
                        previewPanel.setBorder(
                                BorderFactory.createTitledBorder(null,
                                " Question/Answer Preview NOTE: PREVIEW UNORDERED ",
                                TitledBorder.LEFT, 0, 
                                new Font(getFontType, Font.BOLD, 10), 
                                Color.RED));
                        //
                        updateScoreLab.setFont(new Font(getFontType, Font.BOLD, 14));
                        updateScoreLab.setForeground(Color.RED);
                        updateScoreField.setFont(new Font(getFontType, Font.PLAIN, 16));
                        updateScoreField.setForeground(Color.BLACK);
                        updateScoreField.setBorder(BorderFactory.createEtchedBorder());
                        saveButton.setFont(new Font(getFontType, Font.BOLD, 14));//
                        saveButton.setForeground(Color.BLUE);
                        saveButton.setBackground(Color.WHITE);
                        //
                        updateButtonPanel.setBorder(BorderFactory.createEtchedBorder());
                        updateButtonPanel.add(updateScoreLab);
                        updateButtonPanel.add(updateScoreField);
                        updateButtonPanel.add(new JLabel("   "));
                        updateButtonPanel.add(saveButton);
                        //
                        setLayout(new BorderLayout());
                        add(studentNameLab, BorderLayout.NORTH);
                        add(new JScrollPane(previewPanel), BorderLayout.CENTER);
                        add(updateButtonPanel, BorderLayout.SOUTH);
                        validate();
                        //
                        saveButton.addActionListener(new updateStudentTheoryScoreControl());
                    }
                    //
                    class updateStudentTheoryScoreControl implements ActionListener {

                        public void actionPerformed(ActionEvent e) {
                            String scoreFormat = getScoreFormat(updateScoreField.getText());
                            //
                            if(updateScoreField.getText().equalsIgnoreCase("")) {
                                promptLab.setText("Enter Score Before Saving.");
                                JOptionPane.showMessageDialog(null,
                                        promptLab, 
                                        getSchoolName.toUpperCase(),
                                        JOptionPane.INFORMATION_MESSAGE,
                                        infoIcon);
                            }
                            else if(!getOneDetail("select testscore from testscoresprofile "
                                        + "where testsession = '" + testYear + "' "
                                        + "and testterm = '" + testTerm + "' "
                                        + "and testclass = '" + testClass + "' "
                                        + "and questionmode = '" + testMode + "' "
                                        + "and testsubject = '" + testSubject + "' "
                                        + "and registrationnumber = '" + studName + "'").
                                    equalsIgnoreCase("theory")) {
                                //
                                promptLab.setText("Student Score Already Saved. "
                                        + "Please Preview Scores.");
                                JOptionPane.showMessageDialog(null,
                                        promptLab, 
                                        getSchoolName.toUpperCase(),
                                        JOptionPane.INFORMATION_MESSAGE,
                                        infoIcon);
                            }
                            else if(scoreFormat.equalsIgnoreCase("no")) {
                                //
                                promptLab.setText("Student Score Entered Not In "
                                        + "Valid Format. SAMPLE: X/Y%");
                                JOptionPane.showMessageDialog(null,
                                        promptLab, 
                                        getSchoolName.toUpperCase(),
                                        JOptionPane.ERROR_MESSAGE,
                                        errorIcon);
                            }
                            else {
                                //
                                insertUpdateDelete("update testscoresprofile "
                                       + "set testscore = '" + updateScoreField.getText() + "' "
                                          + "where registrationnumber = '" + studName + "' "
                                          + "and testsubject = '" + getTestSubject + "' "
                                          + "and testclass = '" + getTestClass + "' "
                                          + "and testterm = '" + getTestTerm + "' "
                                          + "and testsession = '" + getTestSession + "' "
                                          + "and questionmode = '" + getQuestionMode + "'");
                                //
                                promptLab.setText("Student Theory Assessment Score Saved.");
                                JOptionPane.showMessageDialog(null,
                                        promptLab, 
                                        getSchoolName.toUpperCase(),
                                        JOptionPane.INFORMATION_MESSAGE,
                                        infoIcon);
                                //
                                promptLab.setText("Please Close Dialog When Through "
                                        + "With Student Theory Assessment.");
                                JOptionPane.showMessageDialog(null,
                                        promptLab, 
                                        getSchoolName.toUpperCase(),
                                        JOptionPane.INFORMATION_MESSAGE,
                                        infoIcon);
                                //
                                updateScoreField.setText("");
                            }
                        }
                        //
                        String getScoreFormat(String score) {
                            if(score.contains("/") && 
                                score.contains("%")) {
                               return "yes"; 
                            }
                            else {
                                return "no";
                            }
                        }
                    }
                }
                //
                class saveDelete implements ActionListener {
                    int pos = 0;
                    public saveDelete(int p) {
                        pos = p;
                    }
                    public void actionPerformed(ActionEvent e) {
                        if (testCheckBox[pos].isSelected()) {
                            //check if activity already user for student
                            insertUpdateDelete("update testscoresprofile "
                                + "set registrationnumber = 'nil', "
                                        + "testsubject = 'nil', "
                                        + "testclass = 'nil', "
                                        + "testsession = 'nil', "
                                        + "testterm = 'nil', "
                                        + "questionmode = 'nil', "
                                        + "questionanswered = 'nil', "
                                        + "testscore = 'nil' "
                                + "where registrationnumber = '" + 
                                        testTermField[pos].getText().toLowerCase() + "' "
                                + "and testsubject = '" + testSubject + "' "
                                + "and testclass = '" + testClass + "' "
                                + "and questionmode = '" + testMode + "' "
                                + "and testterm = '" + testTerm + "' "
                                + "and testsession = '" + testYear + "'");
                            //
                            testingDisplayPanel.removeAll();
                            testingDisplayPanel.add(new JLabel(
                                            new ImageIcon("images/loadicon.gif")));
                            validate();
                            //
                            new viewScoreReport(getTestClass, getTestTerm,
                                        getTestSession, getTestSubject,
                                        "score", getQuestionMode, "").start();
                            //
                            testDeleteButton[pos].setForeground(Color.RED);
                            testDeleteButton[pos].setForeground(Color.RED);
                            //
                            if (testCheckBox[pos].isSelected()) {
                                testCheckBox[pos].setSelected(false);
                            }
                        }
                        else {
                            promptLab.setText("Please Check An Candidate Box And "
                                    + "Delete Again");
                            JOptionPane.showMessageDialog(null,
                                    promptLab,
                                    getSchoolName.toUpperCase(),
                                    JOptionPane.INFORMATION_MESSAGE,
                                    infoIcon);
                        }
                    }
                }
               //
                class enableField implements ItemListener {

                    int pos = 0;

                    public enableField(int p) {
                        pos = p;
                    }
                    //
                    public void itemStateChanged(ItemEvent e) {
                        if (testCheckBox[pos].isSelected()) {
                            testSubjectField[pos].setBorder(BorderFactory.createEtchedBorder());
                            testSubjectField[pos].setEditable(true);
                            testSubjectField[pos].setBackground(Color.WHITE);
                            //
                            testSetupButton[pos].setForeground(Color.BLUE);
                        } 
                        else {
                            //
                            if (testCheckBox[pos].getBackground() == Color.LIGHT_GRAY) {
                                testSubjectField[pos].setBackground(Color.LIGHT_GRAY);
                            } 
                            else {
                                testSubjectField[pos].setBackground(themeColor);
                            }
                            //
                            testSubjectField[pos].setBorder(BorderFactory.createEmptyBorder());
                            testSubjectField[pos].setEditable(false);
                            testSetupButton[pos].setForeground(Color.BLUE);
                        }
                    }
                }
                //
                class saveUpdate implements ActionListener {
                    int pos = 0;
                    public saveUpdate(int p) {
                        pos = p;
                    }
                    //
                    public void actionPerformed(ActionEvent e) {
                        if (!testSubjectField[pos].getText().equalsIgnoreCase("")
                                && !getTestClass.equalsIgnoreCase("")
                                && !getQuestionMode.equalsIgnoreCase("")
                                && !getTestTerm.equalsIgnoreCase("")
                                && !getTestSubject.equalsIgnoreCase("")
                                && !getTestSession.equalsIgnoreCase("")) {
                            //
                            testSubjectField[pos].setBorder(BorderFactory.createEmptyBorder());
                            testSubjectField[pos].setEditable(false);
                            testSetupButton[pos].setForeground(Color.BLUE);
                            //
                            if (testCheckBox[pos].isSelected()) {
                                testCheckBox[pos].setSelected(false);
                            }
                            //
                            insertUpdateDelete("update testscoresprofile "
                                    + "set testscore = '" + testSubjectField[pos].getText() + "' "
                                    + "where testclass = '" + getTestClass + "' "
                                    + "and testterm = '" + getTestTerm + "' "
                                    + "and testsession = '" + getTestSession + "' "
                                    + "and questionmode = '" + getQuestionMode + "' "
                                    + "and testsubject = '" + getTestSubject + "' "
                                    + "and registrationnumber = '" + testTermField[pos].getText() + "'");
                            //
                            if (testCheckBox[pos].getBackground() == Color.LIGHT_GRAY) {
                                testSubjectField[pos].setBackground(Color.LIGHT_GRAY);
                            }
                            //
                            new viewScoreReport(getTestClass, getTestTerm,
                                    getTestSession, getTestSubject,
                                    "score", getQuestionMode, "").start(); 
                        } 
                        else {
                            promptLab.setText("Please Enter Data In Empty Field OR "
                                    + "Test Class Not Selected.");
                            JOptionPane.showMessageDialog(null,
                                    promptLab,
                                    getSchoolName.toUpperCase(),
                                    JOptionPane.INFORMATION_MESSAGE,
                                    infoIcon);
                        }
                        //
                        validate();
                    }
                }
            }
            //
            class viewTestSetup extends Thread {

                String getTerm = "", 
                        getMode = "",
                        getSession = "",
                        getClass = "",
                        getSubject = "";
                //
                public viewTestSetup(String tm, String ss, String mode, 
                        String cl, String subj) {
                    getTerm = tm;
                    getSession = ss;
                    getMode = mode;
                    getClass = cl;
                    getSubject = subj;
                }
                //
                public void run() {
                    JPanel subjectBoxPanel = new JPanel();
                    subjectBoxPanel.setBackground(themeColor);
                    //
                    JPanel headerLeftPanel = new JPanel();
                    headerLeftPanel.setBackground(Color.LIGHT_GRAY);
                    //
                    if (!getTerm.equalsIgnoreCase("")) {
                        //
                        resCount = getNumberOfDetails("select * from loadtestprofile "
                                + "where setupterm = '" + getTerm + "' "
                                + "and setupsession = '" + getSession + "' "
                                + "and setupclass = '" + getClass + "' "
                                + "and setupsubject = '" + getSubject + "' "
                                + "and questionmode = '" + getMode + "'");
                    }
                    //
                    if (resCount > 0) {
                        //
                        resList = populateList("select counter from loadtestprofile "
                                    + "where setupterm = '" + getTerm + "' "
                                    + "and setupsession = '" + getSession + "' "
                                    + "and setupclass = '" + getClass + "' "
                                    + "and setupsubject = '" + getSubject + "' "
                                    + "and questionmode = '" + getMode + "' "
                                    + "order by setupclass", resCount);
                        //
                        subjectBoxPanel.setLayout(new GridLayout(resCount + 1, 1, 0, 0));
                        //
                        printExcelText = "";
                        //
                        JPanel headerPanel = new JPanel();
                        headerPanel.setBackground(Color.LIGHT_GRAY);
                        //
                        JTextField option1Lab = new JTextField("TEST SUBJECT", 19);
                        option1Lab.setForeground(Color.BLUE);
                        option1Lab.setFont(new Font(getFontType, Font.BOLD, 14));
                        option1Lab.setBorder(BorderFactory.createEmptyBorder());
                        option1Lab.setBackground(Color.LIGHT_GRAY);
                        option1Lab.setEditable(false);
                        //
                        JTextField option2Lab = new JTextField("CLASS(ES)", 10);
                        option2Lab.setForeground(Color.BLUE);
                        option2Lab.setFont(new Font(getFontType, Font.BOLD, 14));
                        option2Lab.setBorder(BorderFactory.createEmptyBorder());
                        option2Lab.setBackground(Color.LIGHT_GRAY);
                        option2Lab.setEditable(false);
                        //
                        JTextField option5Lab = new JTextField("QUESTIONS PROV.", 11);
                        option5Lab.setForeground(Color.BLUE);
                        option5Lab.setFont(new Font(getFontType, Font.BOLD, 14));
                        option5Lab.setBorder(BorderFactory.createEmptyBorder());
                        option5Lab.setBackground(Color.LIGHT_GRAY);
                        option5Lab.setEditable(false);
                        //
                        JTextField scorePerQuestionLab = new JTextField("SCORE/QUEST.", 9);
                        scorePerQuestionLab.setForeground(Color.BLUE);
                        scorePerQuestionLab.setFont(new Font(getFontType, Font.BOLD, 14));
                        scorePerQuestionLab.setBorder(BorderFactory.createEmptyBorder());
                        scorePerQuestionLab.setBackground(Color.LIGHT_GRAY);
                        scorePerQuestionLab.setEditable(false);
                        //
                        JTextField correctLab = new JTextField("DUR.(MINS)", 7);
                        correctLab.setForeground(Color.BLUE);
                        correctLab.setFont(new Font(getFontType, Font.BOLD, 14));
                        correctLab.setBorder(BorderFactory.createEmptyBorder());
                        correctLab.setBackground(Color.LIGHT_GRAY);
                        correctLab.setEditable(false);
                        //
                        JTextField batchLab = new JTextField("BATCH", 5);
                        batchLab.setForeground(Color.BLUE);
                        batchLab.setFont(new Font(getFontType, Font.BOLD, 14));
                        batchLab.setBorder(BorderFactory.createEmptyBorder());
                        batchLab.setBackground(Color.LIGHT_GRAY);
                        batchLab.setEditable(false);
                        //
                        JTextField displayScoreLab = new JTextField("DISPLAY SCORE", 10);
                        displayScoreLab.setForeground(Color.BLUE);
                        displayScoreLab.setFont(new Font(getFontType, Font.BOLD, 14));
                        displayScoreLab.setBorder(BorderFactory.createEmptyBorder());
                        displayScoreLab.setBackground(Color.LIGHT_GRAY);
                        displayScoreLab.setEditable(false);
                        //
                        headerPanel.add(new JLabel("       "));
                        headerPanel.add(option1Lab);
                        headerPanel.add(option2Lab);
                        headerPanel.add(option5Lab);
                        headerPanel.add(scorePerQuestionLab);
                        headerPanel.add(correctLab);
                        headerPanel.add(batchLab);
                        headerPanel.add(displayScoreLab);
                        //
                        printExcelText += option1Lab.getText() + "\t";
                        printExcelText += option2Lab.getText() + "\t";
                        printExcelText += option5Lab.getText() + "\t";
                        printExcelText += scorePerQuestionLab.getText() + "\t";
                        printExcelText += correctLab.getText() + "\t";
                        printExcelText += batchLab.getText() + "\n";
                        //
                        headerLeftPanel.setLayout(new BorderLayout());
                        headerLeftPanel.add(headerPanel, BorderLayout.WEST);
                        //
                        subjectBoxPanel.add(headerLeftPanel);
                        //
                        testSubjectField = new JTextField[resCount];
                        testClassField = new JTextField[resCount];
                        numberOfTestQuestionField = new JTextField[resCount];
                        testDurationField = new JTextField[resCount];
                        testQuestionBatchField = new JTextField[resCount];
                        testTermField = new JTextField[resCount];
                        //
                        JCheckBox[] previewCheckBox = new JCheckBox[resCount]; 
                        //
                        testCheckBox = new JCheckBox[resCount];
                        testSetupButton = new JButton[resCount];
                        testDeleteButton = new JButton[resCount];
                        //
                        for (int pos = 0; pos < resCount; pos++) {
                            JPanel onePanel = new JPanel();
                            //
                            commTestLab.setText(" Uploading " + (pos + 1) + " Records From "
                                    + "Selected Parameters ...Please Wait!");
                            //
                            testCheckBox[pos] = new JCheckBox();
                            onePanel.add(testCheckBox[pos]);
                            //
                            String bodyText = 
                                    getOneDetail("select setupsubject from loadtestprofile "
                                    + "where counter = '" + resList[pos] + "' "
                                    + "and setupterm = '" + getTerm + "' "
                                    + "and questionmode = '" + getMode + "' "
                                    + "and setupsession = '" + getSession + "'");
                            //
                            printExcelText += bodyText + "\t";
                            //
                            testSubjectField[pos] = new JTextField(bodyText.toUpperCase(), 21);
                            testSubjectField[pos].setForeground(Color.BLACK);
                            testSubjectField[pos].setBorder(BorderFactory.createEmptyBorder());
                            testSubjectField[pos].setFont(new Font(getFontType, Font.PLAIN, 12));
                            testSubjectField[pos].setEditable(false);
                            onePanel.add(testSubjectField[pos]);
                            //
                            bodyText = 
                                    getOneDetail("select setupclass from loadtestprofile "
                                    + "where counter = '" + resList[pos] + "' "
                                    + "and setupterm = '" + getTerm + "' "
                                    + "and setupsession = '" + getSession + "' "
                                    + "and questionmode = '" + getMode + "'");
                            //
                            printExcelText += bodyText + "\t";
                            //
                            testClassField[pos] = new JTextField(bodyText.toUpperCase(), 11);
                            testClassField[pos].setForeground(Color.BLACK);
                            testClassField[pos].setBorder(BorderFactory.createEmptyBorder());
                            testClassField[pos].setFont(new Font(getFontType, Font.PLAIN, 12));
                            testClassField[pos].setEditable(false);
                            onePanel.add(testClassField[pos]);
                            //
                            bodyText = 
                                    getOneDetail("select numberoftestquestions "
                                    + "from loadtestprofile "
                                    + "where counter = '" + resList[pos] + "' "
                                    + "and setupterm = '" + getTerm + "' "
                                    + "and setupsession = '" + getSession + "' "
                                    + "and questionmode = '" + getMode + "'");
                            //
                            printExcelText += bodyText + "\t";
                            //
                            numberOfTestQuestionField[pos] = new JTextField(bodyText, 12);
                            numberOfTestQuestionField[pos].setForeground(Color.BLACK);
                            numberOfTestQuestionField[pos].setBorder(BorderFactory.createEmptyBorder());
                            numberOfTestQuestionField[pos].setFont(new Font(getFontType, Font.PLAIN, 12));
                            numberOfTestQuestionField[pos].setEditable(false);
                            onePanel.add(numberOfTestQuestionField[pos]);
                            //
                            bodyText = 
                                    getOneDetail("select scoreperquestion "
                                    + "from loadtestprofile "
                                    + "where counter = '" + resList[pos] + "' "
                                    + "and setupterm = '" + getTerm + "' "
                                    + "and setupsession = '" + getSession + "' "
                                    + "and questionmode = '" + getMode + "'");
                            //
                            printExcelText += bodyText + "\t";
                            //
                            testTermField[pos] = new JTextField(bodyText, 9);
                            testTermField[pos].setForeground(Color.BLACK);
                            testTermField[pos].setBorder(BorderFactory.createEmptyBorder());
                            testTermField[pos].setFont(new Font(getFontType, Font.PLAIN, 12));
                            testTermField[pos].setEditable(false);
                            onePanel.add(testTermField[pos]);
                            //
                            testDurationField[pos] = new JTextField(
                                    getOneDetail("select testduration from loadtestprofile "
                                    + "where counter = '" + resList[pos] + "' "
                                    + "and setupterm = '" + getTerm + "' "
                                    + "and setupsession = '" + getSession + "' "
                                    + "and questionmode = '" + getMode + "'"), 8);
                            testDurationField[pos].setForeground(Color.BLACK);
                            testDurationField[pos].setBorder(BorderFactory.createEmptyBorder());
                            testDurationField[pos].setFont(new Font(getFontType, Font.PLAIN, 12));
                            testDurationField[pos].setEditable(false);
                            onePanel.add(testDurationField[pos]);
                            //
                            printExcelText += testDurationField[pos].getText() + "\t";
                            //
                            testQuestionBatchField[pos] = new JTextField(
                                    getOneDetail("select questionbatchnumber "
                                    + "from loadtestprofile "
                                    + "where counter = '" + resList[pos] + "' "
                                    + "and setupterm = '" + getTerm + "' "
                                    + "and setupsession = '" + getSession + "' "
                                    + "and questionmode = '" + getMode + "'"), 5);
                            testQuestionBatchField[pos].setForeground(Color.BLACK);
                            testQuestionBatchField[pos].setBorder(BorderFactory.createEmptyBorder());
                            testQuestionBatchField[pos].setFont(new Font(getFontType, Font.PLAIN, 12));
                            testQuestionBatchField[pos].setEditable(false);
                            onePanel.add(testQuestionBatchField[pos]);
                            printExcelText += testQuestionBatchField[pos].getText() + "\n";
                            //
                            String getCtrl = getOneDetail("select displaytestscore "
                                    + "from loadtestprofile "
                                    + "where counter = '" + resList[pos] + "' "
                                    + "and setupterm = '" + getTerm + "' "
                                    + "and setupsession = '" + getSession + "' "
                                    + "and questionmode = '" + getMode + "'");
                            //
                            previewCheckBox[pos] = new JCheckBox();
                            if(getCtrl.equalsIgnoreCase("yes")) {
                                previewCheckBox[pos].setSelected(true);
                            }
                            else {
                                previewCheckBox[pos].setSelected(false);
                            }
                            previewCheckBox[pos].setEnabled(false);
                            onePanel.add(previewCheckBox[pos]);
                            //
                            try{sleep(80);}catch(InterruptedException ererer){}
                            //
                            testSetupButton[pos] = new JButton("Remove");
                            testSetupButton[pos].setForeground(Color.RED);
                            testSetupButton[pos].setBackground(themeColor);
                            testSetupButton[pos].setFont(new Font(getFontType, Font.BOLD, 10));
                            testSetupButton[pos].addActionListener(new saveDelete(pos));
                            //
                            testDeleteButton[pos] = new JButton("Stop Test Progress");
                            testDeleteButton[pos].setForeground(Color.BLUE);
                            testDeleteButton[pos].setBackground(themeColor);
                            testDeleteButton[pos].setFont(new Font("canbria", Font.BOLD, 10));
                            testDeleteButton[pos].addActionListener(new stopTestControl(pos));
                            //
                            onePanel.add(new JLabel("    "));
                            onePanel.add(testSetupButton[pos]);
                            onePanel.add(new JLabel("  "));
                            onePanel.add(testDeleteButton[pos]);
                            //
                            if (Math.IEEEremainder(pos, 2) == 0) {
                                previewCheckBox[pos].setBackground(themeColor);
                                onePanel.setBackground(themeColor);
                                testCheckBox[pos].setBackground(themeColor);
                                testSubjectField[pos].setBackground(themeColor);
                                testTermField[pos].setBackground(themeColor);
                                testClassField[pos].setBackground(themeColor);
                                testQuestionBatchField[pos].setBackground(themeColor);
                                numberOfTestQuestionField[pos].setBackground(themeColor);
                                testDurationField[pos].setBackground(themeColor);
                            } 
                            else {
                                previewCheckBox[pos].setBackground(Color.LIGHT_GRAY);
                                onePanel.setBackground(Color.LIGHT_GRAY);
                                testCheckBox[pos].setBackground(Color.LIGHT_GRAY);
                                testTermField[pos].setBackground(Color.LIGHT_GRAY);
                                testSubjectField[pos].setBackground(Color.LIGHT_GRAY);
                                testClassField[pos].setBackground(Color.LIGHT_GRAY);
                                testQuestionBatchField[pos].setBackground(Color.LIGHT_GRAY);
                                numberOfTestQuestionField[pos].setBackground(Color.LIGHT_GRAY);
                                testDurationField[pos].setBackground(Color.LIGHT_GRAY);
                            }
                            //
                            subjectBoxPanel.add(onePanel);
                        }
                        //
                        commTestLab.setText(" Uploaded " + resCount + " Records From "
                                    + "Selected Parameters.");
                    } 
                    else {
                        subjectBoxPanel.removeAll();
                        subjectBoxPanel.add(new JLabel("No Test Schedule Details Yet!"));
                    }
                    //
                    subjectBoxPanel.setBorder(BorderFactory.createEtchedBorder());
                    //
                    JPanel cenSubjectBoxPanel = new JPanel();
                    cenSubjectBoxPanel.setBackground(themeColor);
                    cenSubjectBoxPanel.add(subjectBoxPanel);
                    //
                    testingDisplayPanel.removeAll();
                    testingDisplayPanel.add(cenSubjectBoxPanel, BorderLayout.WEST);
                    validate();
                }
                //
                class stopTestControl implements ActionListener {
                    int pos = 0;
                    public stopTestControl(int p) {
                        pos = p;
                    }
                    public void actionPerformed(ActionEvent e) {
                        if (testCheckBox[pos].isSelected() 
                                && !getTestClass.equalsIgnoreCase("")
                                && !getTestTerm.equalsIgnoreCase("")
                                && !getTestSubject.equalsIgnoreCase("")
                                && !getQuestionMode.equalsIgnoreCase("")
                                && !getTestSession.equalsIgnoreCase("")) {
                            //
                            if(getOneDetail("select stoptest from stoptestprofile "
                                    + "where setupclass = '" + getTestClass + "' "
                                    + "and setupterm = '" + getTestTerm + "' "
                                    + "and setupsession = '" + getTestSession + "' "
                                    + "and setupsubject = '" + getTestSubject + "' "
                                    + "and questionmode = '" + getQuestionMode + "'").
                                    equalsIgnoreCase("no")) {
                                //
                                promptLab.setText("Do You Want To Stop On-Going Test?");
                                int rep = JOptionPane.showConfirmDialog(null, 
                                        promptLab,
                                        getSchoolName.toUpperCase(),
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.QUESTION_MESSAGE,
                                        questionIcon);
                                //
                                if(rep == JOptionPane.YES_OPTION) {
                                    promptLab.setText("Type Message To Student For Stopping Test.");
                                    String stopMessage = JOptionPane.showInputDialog(null, 
                                            promptLab);
                                    //
                                    insertUpdateDelete("update stoptestprofile "
                                            + "set stoptest = 'yes', "
                                                + "stopmessage = '" + stopMessage.toLowerCase() + "' "
                                            + "where setupclass = '" + getTestClass + "' "
                                            + "and setupterm = '" + getTestTerm + "' "
                                            + "and setupsession = '" + getTestSession + "' "
                                            + "and setupsubject = '" + getTestSubject + "' "
                                            + "and questionmode = '" + getQuestionMode + "'");
                                }
                            }
                            else {
                                promptLab.setText("Test Progress Already Stop.");
                                JOptionPane.showMessageDialog(null,
                                        promptLab,
                                        getSchoolName.toUpperCase(),
                                        JOptionPane.INFORMATION_MESSAGE,
                                        infoIcon);
                            }
                        } 
                        else {
                            promptLab.setText("Please Enter Data In Empty Field OR "
                                    + "Student Class Not Selected.");
                            JOptionPane.showMessageDialog(null,
                                    promptLab,
                                    getSchoolName.toUpperCase(),
                                    JOptionPane.INFORMATION_MESSAGE,
                                    infoIcon);
                        }
                        //
                        validate();
                    }
                }
                //
                class saveDelete implements ActionListener {
                    int pos = 0;
                    public saveDelete(int p) {
                        pos = p;
                    }
                    public void actionPerformed(ActionEvent e) {
                        if (testCheckBox[pos].isSelected() 
                                && !getTestClass.equalsIgnoreCase("")
                                && !getTestTerm.equalsIgnoreCase("")
                                && !getTestSubject.equalsIgnoreCase("")
                                && !getQuestionMode.equalsIgnoreCase("")
                                && !getTestSession.equalsIgnoreCase("")) {
                            //
                            insertUpdateDelete("delete from loadtestprofile "
                                    + "where setupclass = '" + getTestClass + "' "
                                    + "and setupterm = '" + getTestTerm + "' "
                                    + "and setupsession = '" + getTestSession + "' "
                                    + "and setupsubject = '" + getTestSubject + "' "
                                    + "and questionmode = '" + getQuestionMode + "'");
                            //
                            new viewTestSetup(getTestTerm, getTestSession, 
                                    getQuestionMode, getTestClass, 
                                    getTestSubject).start();
                        } 
                        else {
                            promptLab.setText("Please Enter Data In Empty Field OR "
                                    + "Student Class Not Selected.");
                            JOptionPane.showMessageDialog(null,
                                    promptLab,
                                    getSchoolName.toUpperCase(),
                                    JOptionPane.INFORMATION_MESSAGE,
                                    infoIcon);
                        }
                        //
                        validate();
                    }
                }
            }
            //
            class viewTestYearControl implements ActionListener {

                public void actionPerformed(ActionEvent e) {
                    for (int x = 0; x < viewTestYearList.length; x++) {
                        if (x == yearBox.getSelectedIndex()) {
                            getTestSession = viewTestYearList[x].toLowerCase();
                        }
                    }
                }
            }
            //
            class viewTestSubjectControl implements ActionListener {

                public void actionPerformed(ActionEvent e) {
                    for (int x = 0; x < viewTestSubjectList.length; x++) {
                        if (x == subjectBox.getSelectedIndex()) {
                            getTestSubject = viewTestSubjectList[x].toLowerCase();
                        }
                    }
                }
            }
            //
            class viewTestClassControl implements ActionListener {

                public void actionPerformed(ActionEvent e) {
                    for (int x = 0; x < viewTestClassList.length; x++) {
                        if (x == classBox.getSelectedIndex()) {
                            getTestClass = viewTestClassList[x].toLowerCase();
                        }
                    }
                }
            }
            //
            class viewTestTermControl implements ActionListener {

                public void actionPerformed(ActionEvent e) {
                    for (int x = 0; x < viewTestTermList.length; x++) {
                        if (x == termBox.getSelectedIndex()) {
                            getTestTerm = viewTestTermList[x].toLowerCase();
                        }
                    }
                }
            }
        }
        //
        class questionInterfacePanel extends JPanel {
            
            JPanel northPanel = new JPanel(),
                    southPanel = new JPanel(),
                    centerPanel = new JPanel();
            //
            JPanel formPicturePanel = new JPanel(),
                    questionDisplayPanel = new JPanel();
            //
            JPanel questionOptionPanel = new JPanel();
            JLabel newQuestionLab = new JLabel("Designer"),
                    viewQuestionLab = new JLabel("View Questions");
            //
            JPanel buttonPanel = new JPanel(),
                    centbuttonPanel = new JPanel(),
                    centFormPicturePanel = new JPanel(),
                    centFormPanel = new JPanel();
            //view
            JTextField[] questionIDField = null,
                    questionAnswerOption1Field = null,
                    questionAnswerOption2Field = null,
                    questionAnswerOption3Field = null,
                    questionAnswerOption4Field = null,
                    questionAnswerOption5Field = null,
                    answerCorrectOptionField = null,
                    questionBatchField = null;
            //
            String[][] bodyText = null;
            //
            JTextArea[] questionBodyArea = null;
            //
            JCheckBox[] questionCheckBox = null;
            JButton[] questionUpdateButton = null;
            //
            JLabel selectClassLab = new JLabel("Select Class: "),
                    selectTermControlLab = new JLabel(" Term: "),
                    selectSessionLab = new JLabel(" Sess/Year: "),
                    selectBatchLab = new JLabel(" Batch#: "),
                    selectQuestionModeLab = new JLabel(" Question Mode: "),
                    selectSubjectControlLab = new JLabel(" Subject: ");
            //
            String[] viewQuestionClassList = null,
                    viewQuestionStudentList = null,
                    viewQuestionSubjectList = null,
                    viewQuestionYearList = null,
                    viewQuestionBatchList = null,
                    viewQuestionTermList = null, 
                    viewQuestionModeList = {"OBJECTIVE", "THEORY"};
            //
            JComboBox classBox = null,
                    termBox = null,
                    subjectBox = null,
                    yearBox = null,
                    batchBox = null,
                    subjectClassBox = null,
                    questionModeBox = new JComboBox(viewQuestionModeList);
            //view
            JButton searchButton = new JButton("Search Now"),
                    exportButton = new JButton("Export Sheet");
            //
            String getQuestionClass = "",
                    getQuestionTerm = "",
                    getQuestionSubject = "",
                    getQuestionBatch = "",
                    getQuestionMode = "",
                    getTestScheduleAS = "",
                    getQuestionSession = "";//eval the populat
            //   
            private JLabel refreshLab = new JLabel(
                    new ImageIcon("images/refresh.jpg"));
            //
            JPanel classBoxPanel = new JPanel();
            //
            int resCount = 0;
            String[] resList = null;
            //
            JLabel commQuestionLab = new JLabel(" Ready!");
            //
            JPanel refreshLabClassBoxPanel = new JPanel();
            //
            private JPanel formPanel = new JPanel();
            //
            int questionCounter = 0;
            //
            JLabel messageDisplayLab = new JLabel(""); //extra info on the bar
            JPanel imagePreviewPanel = new JPanel(),
                    centImagePreviewPanel = new JPanel();
            //
            JTextField questionBatchNumberField = new JTextField(20);
            //
            JCheckBox enableStudentSectionSummaryCheckBox = 
                    new JCheckBox("Enable Question Explanation");
            //
            public questionInterfacePanel() {
                
                southPanel.setBackground(themeColor);
                centerPanel.setBackground(themeColor);
                northPanel.setBackground(themeColor);
                northPanel.setBorder(BorderFactory.createEtchedBorder());
                //
                JLabel loginDetails = new JLabel("Login As: "
                        + getLoginUserName.toUpperCase() + "   ");
                loginDetails.setFont(new Font(getFontType, Font.BOLD, 14));
                loginDetails.setForeground(Color.ORANGE);
                //
                newQuestionLab.setForeground(Color.BLUE);
                newQuestionLab.setFont(new Font(getFontType, Font.PLAIN, 12));
                newQuestionLab.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                viewQuestionLab.setForeground(Color.BLUE);
                viewQuestionLab.setFont(new Font(getFontType, Font.PLAIN, 12));
                viewQuestionLab.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                //
                commQuestionLab.setForeground(Color.RED);
                commQuestionLab.setFont(new Font(getFontType, Font.BOLD, 10));
                //
                questionOptionPanel.setBackground(themeColor);
                questionOptionPanel.add(new JLabel("   "));
                questionOptionPanel.add(newQuestionLab);
                questionOptionPanel.add(new JLabel(" | "));
                questionOptionPanel.add(viewQuestionLab);
                //
                JPanel optionPanel = new JPanel();
                optionPanel.setLayout(new BorderLayout());
                optionPanel.setBackground(themeColor);
                optionPanel.setBorder(BorderFactory.createEtchedBorder());
                optionPanel.add(loginDetails, BorderLayout.EAST);
                optionPanel.add(questionOptionPanel, BorderLayout.WEST);
                //
                northPanel.setLayout(new BorderLayout());
                northPanel.add(optionPanel, BorderLayout.NORTH);
                northPanel.add(new JLabel(" "), BorderLayout.SOUTH);
                //
                int ct = getNumberOfDetails("select * from classprofile");
                if (ct == 0) {
                    classBox = new JComboBox();
                } else {
                    viewQuestionClassList =
                            populateList("select classname from classprofile "
                            + "order by classname", ct);
                    for (int pos = 0; pos < viewQuestionClassList.length; pos++) {
                        viewQuestionClassList[pos] = viewQuestionClassList[pos].toUpperCase();
                    }
                    classBox = new JComboBox(viewQuestionClassList);
                }
                //
                ct = getNumberOfDetails("select * from termprofile");
                if (ct == 0) {
                    termBox = new JComboBox();
                } else {
                    viewQuestionTermList =
                            populateList("select termname from termprofile", ct);
                    for (int pos = 0; pos < viewQuestionTermList.length; pos++) {
                        viewQuestionTermList[pos] = viewQuestionTermList[pos].toUpperCase();
                    }
                    termBox = new JComboBox(viewQuestionTermList);
                }
                //
                ct = getNumberOfDetails("select * from sessionprofile");
                if (ct == 0) {
                    yearBox = new JComboBox();
                } else {
                    viewQuestionYearList =
                            populateList("select sessionname from sessionprofile", ct);
                    //
                    yearBox = new JComboBox(viewQuestionYearList);
                }
                //
                ct = getNumberOfDetails("select * from subjectprofile");
                if (ct == 0) {
                    subjectBox = new JComboBox();
                } else {
                    viewQuestionSubjectList =
                            populateList("select subjectname from subjectprofile "
                            + "order by subjectname", ct);
                    for (int pos = 0; pos < viewQuestionSubjectList.length; pos++) {
                        viewQuestionSubjectList[pos] = viewQuestionSubjectList[pos].toUpperCase();
                    }
                    subjectBox = new JComboBox(viewQuestionSubjectList);
                }
                //
                ct = getNumberOfDetails("select distinct questionbatchnumber "
                        + "from questionprofile");
                if (ct == 0) {
                    batchBox = new JComboBox();
                } else {
                    viewQuestionBatchList =
                            populateList("select distinct questionbatchnumber "
                            + "from questionprofile "
                            + "order by questionbatchnumber", ct);
                    //
                    batchBox = new JComboBox(viewQuestionBatchList);
                }
                //
                searchButton.setForeground(Color.BLUE);
                searchButton.setBackground(themeColor);
                searchButton.setFont(new Font(getFontType, Font.PLAIN, 10));
                exportButton.setForeground(Color.BLUE);
                exportButton.setBackground(themeColor);
                exportButton.setFont(new Font(getFontType, Font.PLAIN, 10));
                classBox.setForeground(Color.BLACK);
                classBox.setFont(new Font(getFontType, Font.PLAIN, 10));
                termBox.setForeground(Color.BLACK);
                termBox.setFont(new Font(getFontType, Font.PLAIN, 10));
                yearBox.setForeground(Color.BLACK);
                yearBox.setFont(new Font(getFontType, Font.PLAIN, 10));
                batchBox.setForeground(Color.BLACK);
                batchBox.setFont(new Font(getFontType, Font.PLAIN, 10));
                subjectBox.setForeground(Color.BLACK);
                subjectBox.setFont(new Font(getFontType, Font.PLAIN, 10));
                questionModeBox.setForeground(Color.BLACK);
                questionModeBox.setFont(new Font(getFontType, Font.PLAIN, 10));
                selectClassLab.setFont(new Font(getFontType, Font.BOLD, 10));
                selectClassLab.setForeground(Color.RED);
                selectTermControlLab.setFont(new Font(getFontType, Font.BOLD, 10));
                selectTermControlLab.setForeground(Color.RED);
                selectSessionLab.setFont(new Font(getFontType, Font.BOLD, 10));
                selectSessionLab.setForeground(Color.RED);
                selectSubjectControlLab.setFont(new Font(getFontType, Font.BOLD, 10));
                selectSubjectControlLab.setForeground(Color.RED);
                selectBatchLab.setFont(new Font(getFontType, Font.BOLD, 10));
                selectBatchLab.setForeground(Color.RED);
                selectQuestionModeLab.setFont(new Font(getFontType, Font.BOLD, 10));
                selectQuestionModeLab.setForeground(Color.RED);
                refreshLab.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                refreshLab.setToolTipText("Refresh Dropdown Lists");
                //
                classBoxPanel.add(selectClassLab);
                classBoxPanel.add(classBox);
                classBoxPanel.add(selectTermControlLab);
                classBoxPanel.add(termBox);
                classBoxPanel.add(selectSessionLab);
                classBoxPanel.add(yearBox);
                classBoxPanel.add(selectSubjectControlLab);
                classBoxPanel.add(subjectBox);
                classBoxPanel.add(selectBatchLab);
                classBoxPanel.add(batchBox);
                classBoxPanel.add(selectQuestionModeLab);
                classBoxPanel.add(questionModeBox);
                classBoxPanel.add(new JLabel("  "));
                classBoxPanel.add(refreshLab);
                classBoxPanel.add(new JLabel(" "));
                classBoxPanel.add(searchButton);
                classBoxPanel.add(new JLabel(" "));
                classBoxPanel.add(exportButton);
                classBoxPanel.setBackground(Color.WHITE);
                //
                JPanel centClassBoxPanel = new JPanel();
                centClassBoxPanel.setBackground(Color.WHITE);
                centClassBoxPanel.setBorder(BorderFactory.createEtchedBorder());
                centClassBoxPanel.setLayout(new BorderLayout());
                centClassBoxPanel.add(classBoxPanel, BorderLayout.WEST);
                //
                questionDisplayPanel.setLayout(new BorderLayout());
                questionDisplayPanel.setBackground(themeColor);
                //
                JPanel commQuestionDisplayPanel = new JPanel();
                commQuestionDisplayPanel.setLayout(new BorderLayout());
                commQuestionDisplayPanel.setBorder(
                        BorderFactory.createTitledBorder(null,
                        " Available Questions. Check To Make Correction ",
                        TitledBorder.LEFT, 0, new Font(getFontType, Font.BOLD, 10), Color.RED));
                commQuestionDisplayPanel.add(
                        new JScrollPane(questionDisplayPanel), BorderLayout.CENTER);
                commQuestionDisplayPanel.add(commQuestionLab, BorderLayout.SOUTH);
                //
                centerPanel.setLayout(new BorderLayout());
                centerPanel.add(centClassBoxPanel, BorderLayout.NORTH);
                centerPanel.add(commQuestionDisplayPanel, BorderLayout.CENTER);
                //
                setLayout(new BorderLayout());
                add(northPanel, BorderLayout.NORTH);
                add(centerPanel, BorderLayout.CENTER);
                validate();
                //
                batchBox.addActionListener(new viewQuestionBatchControl());
                classBox.addActionListener(new viewQuestionClassControl());
                termBox.addActionListener(new viewQuestionTermControl());
                yearBox.addActionListener(new viewQuestionYearControl());
                questionModeBox.addActionListener(new viewQuestionModeControl());
                subjectBox.addActionListener(new viewQuestionSubjectControl());
                newQuestionLab.addMouseListener(new newQuestionControl());
                viewQuestionLab.addMouseListener(new viewQuestionControl());
                searchButton.addActionListener(new searchQuestionControl());
                exportButton.addActionListener(new exportToExcelControl());
                refreshLab.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        //
                        int ct = getNumberOfDetails("select * from classprofile");
                        if (ct > 0) {
                            viewQuestionClassList = 
                                    populateList("select classname from classprofile "
                                    + "order by classname", ct);
                            //
                            classBox.removeAllItems();
                            for (int pos = 0; pos < viewQuestionClassList.length; pos++) {
                                classBox.addItem(
                                        viewQuestionClassList[pos].toString().toUpperCase());
                            }
                        }
                        //
                        ct = getNumberOfDetails("select * from termprofile");
                        if (ct > 0) {
                            viewQuestionTermList = 
                                    populateList("select termname from termprofile "
                                    + "order by termname", ct);
                            //
                            termBox.removeAllItems();
                            for (int pos = 0; pos < viewQuestionTermList.length; pos++) {
                                termBox.addItem(
                                        viewQuestionTermList[pos].toString().toUpperCase());
                            }
                        }
                        //
                        ct = getNumberOfDetails("select * from sessionprofile");
                        if (ct > 0) {
                            viewQuestionYearList = 
                                    populateList("select sessionname from sessionprofile "
                                    + "order by sessionname", ct);
                            //
                            yearBox.removeAllItems();
                            for (int pos = 0; pos < viewQuestionYearList.length; pos++) {
                                yearBox.addItem(
                                        viewQuestionYearList[pos].toString());
                            }
                        }
                        //
                        ct = getNumberOfDetails("select * from subjectprofile");
                        if (ct > 0) {
                            viewQuestionSubjectList = 
                                    populateList("select subjectname from subjectprofile "
                                    + "order by subjectname", ct);
                            //
                            subjectBox.removeAllItems();
                            for (int pos = 0; pos < viewQuestionSubjectList.length; pos++) {
                                subjectBox.addItem(
                                        viewQuestionSubjectList[pos].toString().toUpperCase());
                            }
                        }
                        //
                        if(getQuestionMode.equalsIgnoreCase("objective")) {
                            ct = getNumberOfDetails("select distinct questionbatchnumber "
                                    + "from questionprofile");
                            if (ct > 0) {
                                viewQuestionBatchList = 
                                        populateList("select distinct questionbatchnumber "
                                        + "from questionprofile "
                                        + "order by questionbatchnumber", ct);
                                //
                                batchBox.removeAllItems();
                                for (int pos = 0; pos < viewQuestionBatchList.length; pos++) {
                                    batchBox.addItem(
                                            viewQuestionBatchList[pos].toString());
                                }
                            }
                        }
                        else if(getQuestionMode.equalsIgnoreCase("theory")) {
                            ct = getNumberOfDetails("select distinct questionbatchnumber "
                                    + "from theoryquestionprofile");
                            if (ct > 0) {
                                viewQuestionBatchList = 
                                        populateList("select distinct questionbatchnumber "
                                        + "from theoryquestionprofile "
                                        + "order by questionbatchnumber", ct);
                                //
                                batchBox.removeAllItems();
                                for (int pos = 0; pos < viewQuestionBatchList.length; pos++) {
                                    batchBox.addItem(
                                            viewQuestionBatchList[pos].toString());
                                }
                            }
                        }
                        //
                        getQuestionMode = "";
                        getQuestionClass = "";
                        getQuestionBatch = "";
                        getQuestionTerm = "";
                        getQuestionSession = "";
                        getQuestionSubject = "";
                        //
                        new newQuestionControl();
                    }
                });
            }
            //
            class viewQuestionControl extends MouseAdapter {
                
                public void mouseClicked(MouseEvent e) {
                    centFormPicturePanel.removeAll();
                    validate();
                }
            }
            //
            class exportToExcelControl implements ActionListener {
                
                public void actionPerformed(ActionEvent e) {
                    //
                    if(resCount == 0) {
                        //
                        promptLab.setText("No Question Summary To Export. "
                                + "Please Make Preview.");
                        JOptionPane.showMessageDialog(null, 
                                promptLab,
                                getSchoolName.toUpperCase(),
                                JOptionPane.INFORMATION_MESSAGE,
                                infoIcon);
                    }
                    else {
                        sendToExcel(getQuestionClass, getQuestionTerm,
                                getQuestionSession, getQuestionSubject, 
                                getQuestionMode);
                    }
                }
            }
            //
            class searchQuestionControl implements ActionListener {

                public void actionPerformed(ActionEvent e) {
                    if (getNumberOfDetails("select * from questionprofile") > 0) {
                        //
                        if (!getQuestionClass.equalsIgnoreCase("") &&
                                !getQuestionTerm.equalsIgnoreCase("") &&
                                !getQuestionSubject.equalsIgnoreCase("") &&
                                !getQuestionMode.equalsIgnoreCase("") &&
                                !getQuestionBatch.equalsIgnoreCase("") &&
                                !getQuestionSession.equalsIgnoreCase("")) {
                            //
                            questionDisplayPanel.removeAll();
                            questionDisplayPanel.add(new JLabel(
                                            new ImageIcon("images/loadicon.gif")));
                            //
                            new viewQuestion(getQuestionClass, 
                                    getQuestionTerm, getQuestionSession, 
                                    getQuestionSubject, getQuestionBatch, 
                                    getQuestionMode).start();
                            validate();
                        } 
                        else {
                            promptLab.setText("Select Class, Term, Session And Subject. "
                                    + "Then Search Again.");
                            JOptionPane.showMessageDialog(null,
                                    promptLab,
                                    getSchoolName.toUpperCase(),
                                    JOptionPane.INFORMATION_MESSAGE,
                                    infoIcon);
                        }
                    } 
                    else {
                        promptLab.setText("No Question Available Yet!");
                        JOptionPane.showMessageDialog(null,
                                promptLab,
                                getSchoolName.toUpperCase(),
                                JOptionPane.INFORMATION_MESSAGE,
                                infoIcon);
                    }
                }
            }
            //
            class newQuestionControl extends MouseAdapter {

                String[] questionParaList = {"SELECT TERM:  ", "SELECT CLASS:  ", 
                                "SELECT SESSION/YEAR:  ", "SELECT SUBJECT:  ", 
                                "QUESTION BATCH NUMBER:  ", "QUESTION MODE:  "};
                //
                JLabel[] questionParaLab = new JLabel[questionParaList.length];
                //
                String[] questionParaModeList = {"OBJECTIVE", "THEORY"};
                //
                JComboBox classParaBox = null,
                        termParaBox = null,
                        sessionParaBox = null,
                        subjectParaBox = null,
                        questionParaModeBox = new JComboBox(questionParaModeList);
                //
                String[] classParaList = null,
                        termParaList = null,
                        sessionParaList = null,
                        subjectParaList = null;
                //
                JButton continueParaButton = new JButton("Continue",
                        new ImageIcon("images/next.jpg")),
                        uploadFromFileButton = new JButton("Upload Questions From File",
                        new ImageIcon("images/next.jpg"));
                //
                JLabel loadFromFileHelpLab = new JLabel(
                        new ImageIcon("images/help1.png"));
                //
                JButton newClassButton = new JButton("     +"),
                        newSessionButton = new JButton("     +"),
                        newSubjectButton = new JButton("     +");
                //
                public void mouseClicked(MouseEvent e) {
                    formPanel.removeAll();
                    formPicturePanel.removeAll();
                    centFormPanel.removeAll();
                    buttonPanel.removeAll();
                    centFormPicturePanel.removeAll();
                    centbuttonPanel.removeAll();
                    imagePreviewPanel.removeAll();
                    centImagePreviewPanel.removeAll();
                    //
                    if(getNumberOfDetails("select * from termprofile") == 0) {
                        insertUpdateDelete("insert into termprofile "
                                + "values ('first')");
                        insertUpdateDelete("insert into termprofile "
                                + "values ('second')");
                        insertUpdateDelete("insert into termprofile "
                                + "values ('third')");
                    }
                    //
                    int ct = getNumberOfDetails("select * from classprofile");
                    if (ct == 0) {
                        classParaBox = new JComboBox();
                    } 
                    else {
                        classParaList = populateList("select classname from classprofile "
                                + "order by classname", ct);
                        //
                        for (int pos = 0; pos < classParaList.length; pos++) {
                            classParaList[pos] = classParaList[pos].toUpperCase();
                        }
                        classParaBox = new JComboBox(classParaList);
                    }
                    //
                    ct = getNumberOfDetails("select * from termprofile");
                    if (ct == 0) {
                        termParaBox = new JComboBox();
                    } 
                    else {
                        termParaList = populateList("select termname from termprofile "
                                + "order by termname", ct);
                        //
                        for (int pos = 0; pos < termParaList.length; pos++) {
                            termParaList[pos] = termParaList[pos].toUpperCase();
                        }
                        termParaBox = new JComboBox(termParaList);
                    }
                    //
                    ct = getNumberOfDetails("select * from sessionprofile");
                    if (ct == 0) {
                        sessionParaBox = new JComboBox();
                    } 
                    else {
                        sessionParaList = 
                                populateList("select sessionname from sessionprofile "
                                + "order by sessionname", ct);
                        //
                        sessionParaBox = new JComboBox(sessionParaList);
                    }
                    //
                    ct = getNumberOfDetails("select * from subjectprofile");
                    if (ct == 0) {
                        subjectParaBox = new JComboBox();
                    } 
                    else {
                        subjectParaList = 
                                populateList("select subjectname from subjectprofile "
                                + "order by subjectname", ct);
                        //
                        for (int pos = 0; pos < subjectParaList.length; pos++) {
                            subjectParaList[pos] = subjectParaList[pos].toUpperCase();
                        }
                        subjectParaBox = new JComboBox(subjectParaList);
                    }
                    //
                    formPanel.setLayout(new GridLayout(questionParaList.length, 2, 0, 5));
                    formPanel.setBorder(
                            BorderFactory.createTitledBorder(null,
                            " Parameter Section ",
                            TitledBorder.LEFT, 0, new Font(getFontType, Font.BOLD, 10), 
                            Color.BLUE));
                    //
                    imagePreviewPanel.setBackground(themeColor);
                    imagePreviewPanel.setBorder(
                            BorderFactory.createTitledBorder(null,
                            " Image Preview ",
                            TitledBorder.LEFT, 0, new Font(getFontType, Font.BOLD, 10), 
                            Color.BLUE));
                    //
                    ct = 0;
                    for (int pos = 0; pos < questionParaList.length; pos++) {
                        questionParaLab[pos] = new JLabel(questionParaList[pos], JLabel.RIGHT);
                        questionParaLab[pos].setFont(new Font(getFontType, Font.BOLD, 14));
                        questionParaLab[pos].setForeground(Color.RED);
                    }
                    //
                    refreshLabClassBoxPanel.setBackground(themeColor);
                    refreshLabClassBoxPanel.setLayout(new BorderLayout());
                    refreshLabClassBoxPanel.add(classParaBox, BorderLayout.WEST);
                    //
                    questionBatchNumberField.setForeground(Color.BLACK);
                    questionBatchNumberField.setFont(new Font(getFontType, Font.PLAIN, 14));
                    questionBatchNumberField.setBorder(BorderFactory.createEtchedBorder());
                    newClassButton.setFont(new Font(getFontType, Font.BOLD, 20));
                    newClassButton.setForeground(Color.RED);
                    newClassButton.setBackground(themeColor);
                    newClassButton.setBorder(BorderFactory.createEmptyBorder());
                    newClassButton.addActionListener(new addNewClassNameControl());
                    newClassButton.setToolTipText("Click To Create New Class");
                    newSessionButton.setFont(new Font(getFontType, Font.BOLD, 20));
                    newSessionButton.setForeground(Color.RED);
                    newSessionButton.setBackground(themeColor);
                    newSessionButton.setBorder(BorderFactory.createEmptyBorder());
                    newSessionButton.setToolTipText("Click To Create New Session");
                    newSessionButton.addActionListener(new addNewSessionNameControl());
                    newSubjectButton.setFont(new Font(getFontType, Font.BOLD, 20));
                    newSubjectButton.setForeground(Color.RED);
                    newSubjectButton.setBackground(themeColor);
                    newSubjectButton.setBorder(BorderFactory.createEmptyBorder());
                    newSubjectButton.setToolTipText("Click To Create New Subject");
                    newSubjectButton.addActionListener(new addNewSubjectNameControl());
                    //
                    formPanel.add(questionParaLab[0]);
                    formPanel.add(termParaBox);
                    //
                    formPanel.add(questionParaLab[1]);
                    JPanel classParaPanel = new JPanel();
                    classParaPanel.setBackground(Color.WHITE);
                    classParaPanel.setLayout(new BorderLayout());
                    classParaPanel.add(classParaBox, BorderLayout.CENTER);
                    classParaPanel.add(newClassButton, BorderLayout.EAST);
                    formPanel.add(classParaPanel);
                    //
                    formPanel.add(questionParaLab[2]);
                    JPanel sessionParaPanel = new JPanel();
                    sessionParaPanel.setBackground(Color.WHITE);
                    sessionParaPanel.setLayout(new BorderLayout());
                    sessionParaPanel.add(sessionParaBox, BorderLayout.CENTER);
                    sessionParaPanel.add(newSessionButton, BorderLayout.EAST);
                    formPanel.add(sessionParaPanel);
                    //
                    formPanel.add(questionParaLab[3]);
                    JPanel subjectParaPanel = new JPanel();
                    subjectParaPanel.setBackground(Color.WHITE);
                    subjectParaPanel.setLayout(new BorderLayout());
                    subjectParaPanel.add(subjectParaBox, BorderLayout.CENTER);
                    subjectParaPanel.add(newSubjectButton, BorderLayout.EAST);
                    formPanel.add(subjectParaPanel);
                    //
                    formPanel.add(questionParaLab[4]);
                    formPanel.add(questionBatchNumberField);
                    //
                    formPanel.add(questionParaLab[5]);
                    formPanel.add(questionParaModeBox);
                    //
                    classParaBox.setFont(new Font(getFontType, Font.PLAIN, 12));
                    classParaBox.setForeground(Color.BLACK);
                    termParaBox.setFont(new Font(getFontType, Font.PLAIN, 12));
                    termParaBox.setForeground(Color.BLACK);
                    subjectParaBox.setFont(new Font(getFontType, Font.PLAIN, 12));
                    subjectParaBox.setForeground(Color.BLACK);
                    sessionParaBox.setFont(new Font(getFontType, Font.PLAIN, 12));
                    sessionParaBox.setForeground(Color.BLACK);
                    questionParaModeBox.setFont(new Font(getFontType, Font.PLAIN, 12));
                    questionParaModeBox.setForeground(Color.BLACK);
                    //
                    formPicturePanel.add(formPanel);
                    //
                    continueParaButton.setVerticalTextPosition(AbstractButton.CENTER);
                    continueParaButton.setHorizontalTextPosition(AbstractButton.CENTER);
                    continueParaButton.setFont(new Font(getFontType, Font.BOLD, 10));
                    continueParaButton.setForeground(Color.RED);
                    continueParaButton.setBackground(Color.WHITE);
                    continueParaButton.setEnabled(false);
                    //
                    uploadFromFileButton.setVerticalTextPosition(AbstractButton.CENTER);
                    uploadFromFileButton.setHorizontalTextPosition(AbstractButton.CENTER);
                    uploadFromFileButton.setForeground(Color.BLUE);
                    uploadFromFileButton.setFont(new Font(getFontType, Font.BOLD, 10));
                    uploadFromFileButton.setBackground(Color.WHITE);
                    uploadFromFileButton.setEnabled(false);
                    enableStudentSectionSummaryCheckBox.setForeground(Color.BLACK);
                    enableStudentSectionSummaryCheckBox.setFont(new Font(getFontType, Font.BOLD, 10));
                    enableStudentSectionSummaryCheckBox.setBackground(Color.WHITE);
                    //
                    loadFromFileHelpLab.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    //
                    messageDisplayLab.setText("  Select Desired Parameters");
                    messageDisplayLab.setFont(new Font(getFontType, Font.PLAIN, 12));
                    messageDisplayLab.setForeground(Color.BLACK);
                    //
                    buttonPanel.setLayout(new FlowLayout());
                    buttonPanel.setBackground(Color.WHITE);
                    buttonPanel.add(enableStudentSectionSummaryCheckBox);
                    buttonPanel.add(new JLabel("  "));
                    buttonPanel.add(continueParaButton);
                    buttonPanel.add(new JLabel("     "));
                    buttonPanel.add(uploadFromFileButton);
                    buttonPanel.add(new JLabel("  "));
                    buttonPanel.add(loadFromFileHelpLab);
                    //
                    centbuttonPanel.setBackground(Color.WHITE);
                    centbuttonPanel.setLayout(new BorderLayout());
                    centbuttonPanel.setBorder(BorderFactory.createEtchedBorder());
                    centbuttonPanel.add(buttonPanel, BorderLayout.CENTER);
                    centbuttonPanel.add(messageDisplayLab, BorderLayout.WEST);
                    //
                    centImagePreviewPanel.setBackground(themeColor);
                    centImagePreviewPanel.add(new JScrollPane(imagePreviewPanel));
                    //
                    centFormPicturePanel.setLayout(new BorderLayout());
                    centFormPicturePanel.add(formPicturePanel, BorderLayout.WEST);
                    centFormPicturePanel.add(
                            new JScrollPane(centImagePreviewPanel), BorderLayout.EAST);
                    centFormPicturePanel.add(centbuttonPanel, BorderLayout.SOUTH);
                    //
                    centFormPanel.setLayout(new BorderLayout());
                    centFormPanel.add(centFormPicturePanel, BorderLayout.CENTER);
                    //
                    northPanel.add(centFormPanel, BorderLayout.CENTER);
                    validate();
                    //
                    formPicturePanel.setBackground(themeColor);
                    formPanel.setBackground(themeColor);
                    centFormPicturePanel.setBackground(themeColor);
                    centFormPanel.setBackground(themeColor);
                    //
                    loadFromFileHelpLab.addMouseListener(new displayPicturalViewControl());
                    uploadFromFileButton.addActionListener(new uploadQuestionFromFileControl());
                    sessionParaBox.addActionListener(new selectSessionControl());
                    classParaBox.addActionListener(new selectClassControl());
                    termParaBox.addActionListener(new selectTermControl());
                    subjectParaBox.addActionListener(new selectSubjectControl());
                    questionParaModeBox.addActionListener(new questionModeControl());
                    continueParaButton.addActionListener(new displayEntryPage());
                }
                //
                class questionModeControl implements ActionListener {
                    
                    public void actionPerformed(ActionEvent e) {
                        for(int pos = 0; pos < questionParaModeList.length; pos++) {
                            if(pos == questionParaModeBox.getSelectedIndex()) {
                                getQuestionMode = questionParaModeList[pos].toLowerCase();
                            }
                        }
                        //
                        buttonPanel.removeAll();
                        buttonPanel.setLayout(new FlowLayout());
                        buttonPanel.setBackground(Color.WHITE);
                        //
                        if(getQuestionMode.equalsIgnoreCase("objective")) {
                            buttonPanel.add(enableStudentSectionSummaryCheckBox);
                            buttonPanel.add(new JLabel("  "));
                        }
                        buttonPanel.add(continueParaButton);
                        buttonPanel.add(new JLabel("     "));
                        buttonPanel.add(uploadFromFileButton);
                        buttonPanel.add(new JLabel("  "));
                        buttonPanel.add(loadFromFileHelpLab);
                        validate();
                    }
                }
                //
                class displayPicturalViewControl extends MouseAdapter {
                    
                    public void mouseClicked(MouseEvent e) {
                        JPanel viewPanel = new JPanel();
                        viewPanel.setBackground(Color.WHITE);
                        viewPanel.add(new JLabel(new ImageIcon("images/snapshot.png")));
                        //
                        JOptionPane.showMessageDialog(null,
                                viewPanel,
                                getSchoolName.toUpperCase(),
                                JOptionPane.PLAIN_MESSAGE);
                        return;
                    }
                }
                //
                class uploadQuestionFromFileControl implements ActionListener {
                    
                    public void actionPerformed(ActionEvent e) {
                        
                        String msg = "INSTRUCTIONS FOR UPLOADING QUESTIONS TO SERVERS\n"
                                + "Downloaded Questions MUST FOLLOW THIS PATTERN For Easy Uploading\n"
                                + "..........................................................................."
                                + "...........................................................................\n"
                                + "DOWNLOADING FROM THR INTERNET\n"
                                + "..........................................................................."
                                + "...........................................................................\n"
                                + "1. Download Any Past Question Of Choice\n"
                                + "2. While On The Internet, Convert The Download Format To "
                                + "Microsoft Word If Not In Microsoft Word Downloadable Format.\n"
                                + "3. Open The Microsoft Word Document, Highlight Whole Document, Click PageLayout, "
                                + "Click Columns, Click One.\n"
                                + "4. Click File, Click Save As, Save Document As A 'Plain Text' File, Select Saving Location, "
                                + "Click Save Button.\n"
                                + "5. Open The Text Document, Insert A Blank Row Between Each Question, "
                                + "Including Before Question 1 And After The Last Question.\n"
                                + "6. Remove The Question Numbering Tag For Each Question.\n"
                                + "7. Collapse Each Question And Its Options To Become A Single Sentence Paragraph.\n"
                                + "NOTE: THE OPTIONS' LETTER MUST HAVE A DOT AFTER IT AND MUST BE IN UPPERCASE "
                                + "STYLE(eg A.)\n"
                                + "8. IF THE QUESTION HAS NO OPTION E, TYPE NONE AS OPTION E (ie E.NONE)\n"
                                + "9. IF QUESTION HAS ANSWER OPTION PROVIDED, "
                                + "ADD IT AFTER THE E. OPTION AND PREFIX IT WITH "
                                + "'ANS.' FOLLOW BY THE CORRECT OPTION LETTER. (eg ANS.C)\n"
                                + "10. IF THE QUESTION HAS NO ANSWER OPTION PROVIDED, "
                                + "ALSO ADD IT AFTER THE E. OPTION AND PREFIX IT WITH ANS.o (small letter o)\n"
                                + "11. ENSURE A QUESTION COMPONENT ARRANGES UP TO A SINGLE SENTENCE\n"
                                + "12. Click Help Icon For Pictural View Of Sample\n"
                                + "13. In The Picture NOTES The Option A. ... B. ... C. ... D. ... E. ... ANS. \n"
                                + "14. Also NOTES The Blank Row Before The First Paragraph And After The "
                                + "Last Paragraph\n"
                                + "15. If Question Details Needs Modification, Search Out Question "
                                + "Details And Update Information Accordingly\n"
                                + "16. For Theory Questions, TYPE EACH QUESTION AS A PARAGRAPH AND EACH "
                                + "PARAGRAPH MUST END WITH THE TAG '.#'\n"
                                + "17. FOR UPLOADING IMAGE QUESTIONS OR IMAGE ANSWER OPTIONS, "
                                + "FIRST COPY THE IMAGES FILES TO YOUR LOCATION OF CHOICE "
                                + "AND UPLOAD IMAGES FROM THERE.\n"
                                + "18. Please Follow Instruction For Proper Upload.\n\n"
                                + "Do You Want To Continue?";
                        //
                        JTextArea instrMsg = new JTextArea(msg, 20, 70);
                        instrMsg.setForeground(Color.BLUE);
                        instrMsg.setLineWrap(true);
                        instrMsg.setWrapStyleWord(true);
                        instrMsg.setFont(new Font(getFontType, Font.PLAIN, 14));
                        instrMsg.setEditable(false);
                        //
                        int rep = JOptionPane.showConfirmDialog(null, 
                                new JScrollPane(instrMsg),
                                getSchoolName.toUpperCase(),
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                questionIcon);
                        //
                        if(rep == JOptionPane.YES_OPTION) {
                            if(getQuestionClass.equalsIgnoreCase("") || 
                               getQuestionSession.equalsIgnoreCase("") || 
                               getQuestionTerm.equalsIgnoreCase("") || 
                               getQuestionMode.equalsIgnoreCase("") || 
                               getQuestionSubject.equalsIgnoreCase("") ||
                               questionBatchNumberField.getText().equalsIgnoreCase("")) {
                                //
                                promptLab.setText("Empty Field Detected");
                                JOptionPane.showMessageDialog(null,
                                        promptLab,
                                        getSchoolName.toUpperCase(),
                                        JOptionPane.INFORMATION_MESSAGE,
                                        infoIcon);
                            }
                            else {
                                new startUploadProcess().start();
                            }
                        }
                    }
                    //
                    class startUploadProcess extends Thread {
                        
                        public void run() {
                            try {
                                JFileChooser ch = new JFileChooser();
                                ch.setFileSelectionMode(ch.FILES_ONLY);
                                ch.setDialogTitle("Upload Process...");                            
                                ch.setMultiSelectionEnabled(true);
                                //display the file dialog box
                                int rep = ch.showOpenDialog(null);
                                if (rep == ch.CANCEL_OPTION) {
                                   return;
                                }
                                File dataFile = null;
                                //get file and check for no file selected
                                dataFile = ch.getSelectedFile();
                                if (dataFile.equals(null) ||
                                       dataFile.getName().equalsIgnoreCase("")) {
                                    //
                                    promptLab.setText(" File Opening Process. No File Opened.");
                                    JOptionPane.showMessageDialog(null, 
                                            promptLab,
                                            getSchoolName.toUpperCase(),
                                            JOptionPane.INFORMATION_MESSAGE,
                                            infoIcon);
                                } 
                                else {
                                    String format = "";
                                    //change the back slash to front slash
                                    for(int x = 0; x < dataFile.getAbsolutePath().length(); x++) {
                                        if (dataFile.getAbsolutePath().charAt(x) == '\\') {
                                            format = dataFile.getAbsolutePath().replaceAll("\\\\", "/");
                                        }
                                    }
                                    //
                                    BufferedReader in = new BufferedReader(
                                            new InputStreamReader(new FileInputStream(format)));
                                    //
                                    if(getQuestionMode.equalsIgnoreCase("theory")) {
                                        //
                                        while(in.readLine() != null) {
                                            String readFromEachLine = in.readLine();
                                            //
                                            int startLetPos = 0,
                                                stopLetPos = readFromEachLine.indexOf(".#");//stop at a
                                            //
                                            String question = readFromEachLine.substring(
                                                 startLetPos, stopLetPos);
                                            //
                                            question = question.replaceAll("'", "");
                                            question = question.replaceAll(";", "");
                                            question = question.replaceAll("-", "");
                                            question = question.replaceAll("\t", "");
                                            //
                                            if(question.charAt(question.length() - 1) == '.') {
                                                question = question.substring(0, question.length() - 1);
                                            }
                                            //
                                            int counter = 
                                                    (getNumberOfDetails("select * from theoryquestionprofile") + 1),
                                                //
                                                questionID = (getNumberOfDetails("select * from theoryquestionprofile "
                                                    + "where questionclass = '" + getQuestionClass.toLowerCase() + "' "
                                                    + "and questionterm = '" + getQuestionTerm.toLowerCase() + "' "
                                                    + "and questionsession = '" + getQuestionSession + "' "
                                                    + "and questionmode = '" + getQuestionMode + "' "
                                                    + "and questionsubject = '" + getQuestionSubject.toLowerCase() + "'") + 1);
                                            //
                                            try{sleep(80);}catch(InterruptedException erer){}
                                            //
                                            if(getNumberOfDetails("select * from theoryquestionprofile "
                                                    + "where questionclass = '" + getQuestionClass.toLowerCase() + "' "
                                                    + "and questionterm = '" + getQuestionTerm.toLowerCase() + "' "
                                                    + "and questionsession = '" + getQuestionSession + "' "
                                                    + "and questionmode = '" + getQuestionMode + "' "
                                                    + "and questionsubject = '" + getQuestionSubject.toLowerCase() + "' "
                                                    + "and questionbody = '" + question + "'") > 0) {
                                                //
                                                insertUpdateDelete("update theoryquestionprofile "
                                                    + "set questionbody = '" + question + "' "
                                                        + "where questionclass = '" + getQuestionClass + "' "
                                                        + "and questionterm = '" + getQuestionTerm + "' "
                                                        + "and questionsession = '" + getQuestionSession + "' "
                                                        + "and questionsubject = '" + getQuestionSubject + "' "
                                                        + "and questionmode = '" + getQuestionMode + "' "
                                                        + "and questionid = '" + questionID + "' "
                                                        + "and questionbatchnumber = '" + 
                                                        questionBatchNumberField.getText() + "'");
                                            }
                                            else {
                                                //
                                                insertUpdateDelete("insert into theoryquestionprofile "
                                                    + "values ('" + counter + "', "
                                                            + "'" + questionID + "', "
                                                            + "'" + questionBatchNumberField.getText() + "', "
                                                            + "'" + question + "', "
                                                            + "'" + getQuestionClass.toLowerCase() + "', "
                                                            + "'" + getQuestionTerm.toLowerCase() + "', "
                                                            + "'" + getQuestionSession + "', "
                                                            + "'" + getQuestionSubject.toLowerCase() + "', "
                                                            + "'" + getQuestionMode.toLowerCase() + "', "
                                                            + "'" + day + " " + letterMonth(Integer.parseInt(mon)) + 
                                                                " 20" + year + "')");
                                            }
                                            //
                                            try{sleep(80);}catch(InterruptedException erer){}
                                        }
                                    }
                                    else if(getQuestionMode.equalsIgnoreCase("objective")) {
                                        String[] letterControl = {"A.", "B.", "C.", "D.", "E.", "ANS."};
                                        //
                                        while(in.readLine() != null) {
                                            String readFromEachLine = in.readLine();
                                            //
                                            int startLetPos = 0,
                                                stopLetPos = readFromEachLine.indexOf(letterControl[0]);//stop at a
                                            //
                                            String question = readFromEachLine.substring(
                                                 startLetPos, stopLetPos);
                                            //
                                            startLetPos = stopLetPos + 2;
                                            stopLetPos = readFromEachLine.indexOf(letterControl[1]);//stop at b
                                            //
                                            String optionA = readFromEachLine.substring(
                                                 startLetPos, stopLetPos);
                                            //
                                            startLetPos = stopLetPos + 2;
                                            stopLetPos = readFromEachLine.indexOf(letterControl[2]);//stop at c
                                            //
                                            String optionB = readFromEachLine.substring(
                                                 startLetPos, stopLetPos);
                                            //
                                            startLetPos = stopLetPos + 2;
                                            stopLetPos = readFromEachLine.indexOf(letterControl[3]);//stop at d
                                            //
                                            String optionC = readFromEachLine.substring(
                                                 startLetPos, stopLetPos);
                                            //
                                            startLetPos = stopLetPos + 2;
                                            stopLetPos = readFromEachLine.indexOf(letterControl[4]);//stop at e
                                            //
                                            String optionD = readFromEachLine.substring(
                                                 startLetPos, stopLetPos);
                                            //
                                            startLetPos = stopLetPos + 2;
                                            stopLetPos = readFromEachLine.indexOf(letterControl[5]);//stop at e
                                            //
                                            String optionE = readFromEachLine.substring(
                                                 startLetPos, stopLetPos);
                                            //
                                            startLetPos = stopLetPos + 4;
                                            //
                                            String correctOption = readFromEachLine.substring(
                                                 startLetPos);
                                            //
                                            question = question.replaceAll("'", "");
                                            optionA = optionA.replaceAll("'", "");
                                            optionB = optionB.replaceAll("'", "");
                                            optionC = optionC.replaceAll("'", "");
                                            optionD = optionD.replaceAll("'", "");
                                            optionE = optionE.replaceAll("'", "");
                                            //
                                            question = question.replaceAll(";", "");
                                            optionA = optionA.replaceAll(";", "");
                                            optionB = optionB.replaceAll(";", "");
                                            optionC = optionC.replaceAll(";", "");
                                            optionD = optionD.replaceAll(";", "");
                                            optionE = optionE.replaceAll(";", "");
                                            //
                                            question = question.replaceAll("-", "");
                                            optionA = optionA.replaceAll("-", "");
                                            optionB = optionB.replaceAll("-", "");
                                            optionC = optionC.replaceAll("-", "");
                                            optionD = optionD.replaceAll("-", "");
                                            optionE = optionE.replaceAll("-", "");
                                            //
                                            question = question.replaceAll("\t", "");
                                            optionA = optionA.replaceAll("\t", "");
                                            optionB = optionB.replaceAll("\t", "");
                                            optionC = optionC.replaceAll("\t", "");
                                            optionD = optionD.replaceAll("\t", "");
                                            optionE = optionE.replaceAll("\t", "");
                                            //
                                            if(question.charAt(question.length() - 1) == '.') {
                                                question = question.substring(0, question.length() - 1);
                                            }
                                            if(optionA.charAt(optionA.length() - 1) == '.') {
                                                optionA = optionA.substring(0, optionA.length() - 1);
                                            }
                                            if(optionB.charAt(optionB.length() - 1) == '.') {
                                                optionB = optionB.substring(0, optionB.length() - 1);
                                            }
                                            if(optionC.charAt(optionC.length() - 1) == '.') {
                                                optionC = optionC.substring(0, optionC.length() - 1);
                                            }
                                            if(optionD.charAt(optionD.length() - 1) == '.') {
                                                optionD = optionD.substring(0, optionD.length() - 1);
                                            }
                                            if(optionE.charAt(optionE.length() - 1) == '.') {
                                                optionE = optionE.substring(0, optionE.length() - 1);
                                            }
                                            //
                                            if(optionA.startsWith("A.")) {
                                                optionA = optionA.substring(optionA.indexOf("A.") + 2);
                                            }
                                            if(optionB.startsWith("B.")) {
                                                optionB = optionB.substring(0, optionB.indexOf("B.") + 2);
                                            }
                                            if(optionC.startsWith("C.")) {
                                                optionC = optionC.substring(0, optionC.indexOf("C.") + 2);
                                            }
                                            if(optionD.startsWith("D.")) {
                                                optionD = optionD.substring(0, optionD.indexOf("D.") + 2);
                                            }
                                            if(optionE.startsWith("E.")) {
                                                optionE = optionE.substring(0, optionE.indexOf("E.") + 2);
                                            }
                                            //
                                            correctOption = correctOption.replaceAll("\t", "");
                                            correctOption = correctOption.replaceAll(" ", "");
                                            //
                                            int counter = 
                                                    (getNumberOfDetails("select * from questionprofile") + 1),
                                                //
                                                questionID = (getNumberOfDetails("select * from questionprofile "
                                                    + "where questionclass = '" + getQuestionClass.toLowerCase() + "' "
                                                    + "and questionterm = '" + getQuestionTerm.toLowerCase() + "' "
                                                    + "and questionsession = '" + getQuestionSession + "' "
                                                    + "and questionmode = '" + getQuestionMode + "' "
                                                    + "and questionsubject = '" + getQuestionSubject.toLowerCase() + "'") + 1);
                                            //
                                            try{sleep(80);}catch(InterruptedException erer){}
                                            //
                                            if(getNumberOfDetails("select * from questionprofile "
                                                    + "where questionclass = '" + getQuestionClass.toLowerCase() + "' "
                                                    + "and questionterm = '" + getQuestionTerm.toLowerCase() + "' "
                                                    + "and questionsession = '" + getQuestionSession + "' "
                                                    + "and questionmode = '" + getQuestionMode + "' "
                                                    + "and questionsubject = '" + getQuestionSubject.toLowerCase() + "' "
                                                    + "and questionbody = '" + question + "'") > 0) {
                                                //
                                                insertUpdateDelete("update questionprofile "
                                                    + "set questionbody = '" + question + "', "
                                                        + "answeroptiona = '" + optionA + "', "
                                                        + "answeroptionb = '" + optionB + "', "
                                                        + "answeroptionc = '" + optionC + "', "
                                                        + "answeroptiond = '" + optionD + "', "
                                                        + "answeroptione = '" + optionE + "', "
                                                        + "correctoption = '" + correctOption + "' "
                                                        + "where questionclass = '" + getQuestionClass + "' "
                                                        + "and questionterm = '" + getQuestionTerm + "' "
                                                        + "and questionsession = '" + getQuestionSession + "' "
                                                        + "and questionsubject = '" + getQuestionSubject + "' "
                                                        + "and questionmode = '" + getQuestionMode + "' "
                                                        + "and questionid = '" + questionID + "' "
                                                        + "and questionbatchnumber = '" + 
                                                        questionBatchNumberField.getText() + "'");
                                            }
                                            else {
                                                //
                                                insertUpdateDelete("insert into questionprofile "
                                                    + "values ('" + counter + "', "
                                                            + "'" + questionID + "', "
                                                            + "'" + questionBatchNumberField.getText() + "', "
                                                            + "'" + question + "', "
                                                            + "'" + optionA + "', "
                                                            + "'" + optionB + "', "
                                                            + "'" + optionC + "', "
                                                            + "'" + optionD + "', "
                                                            + "'" + optionE + "', "
                                                            + "'" + correctOption.toLowerCase() + "', "
                                                            + "'" + getQuestionClass.toLowerCase() + "', "
                                                            + "'" + getQuestionTerm.toLowerCase() + "', "
                                                            + "'" + getQuestionSession + "', "
                                                            + "'" + getQuestionSubject.toLowerCase() + "', "
                                                            + "'" + getQuestionMode.toLowerCase() + "', "
                                                            + "'" + day + " " + letterMonth(Integer.parseInt(mon)) + 
                                                                " 20" + year + "')");
                                            }
                                            //
                                            try{sleep(80);}catch(InterruptedException erer){}
                                        }
                                    }
                                    //
                                    getQuestionClass = "";
                                    getQuestionSession = "";
                                    getQuestionTerm = "";
                                    getQuestionSubject = "";
                                    getQuestionMode = "";
                                    questionBatchNumberField.setText("");
                                    //
                                    promptLab.setText("Uploaded Records Completed.");
                                    JOptionPane.showMessageDialog(null, 
                                            promptLab,
                                            getSchoolName.toUpperCase(),
                                            JOptionPane.INFORMATION_MESSAGE,
                                            infoIcon);
                                }
                            }catch(Exception erer){}
                        }
                    }
                }
                //
                class addNewSubjectNameControl implements ActionListener {
                    
                    public void actionPerformed(ActionEvent e) {
                        try {
                            promptLab.setText("Enter New Subject Name");
                            String getSubjName = JOptionPane.showInputDialog(null, 
                                    promptLab);
                            //
                            if(getNumberOfDetails("select * from subjectprofile "
                                    + "where subjectname = '" + getSubjName.toLowerCase() + "'") > 0) {
                                //
                                promptLab.setText("Exalt Subject Name Already Exist");
                                JOptionPane.showMessageDialog(null, 
                                        promptLab,
                                        getSchoolName.toUpperCase(),
                                        JOptionPane.INFORMATION_MESSAGE,
                                        infoIcon);
                            }
                            else {
                                //
                                promptLab.setText("Does Subject Name " + getSubjName.toUpperCase() + " "
                                        + "Spelling Accurate Of Choice?");
                                int rep = JOptionPane.showConfirmDialog(null,
                                        promptLab,
                                        getSchoolName.toUpperCase(),
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.QUESTION_MESSAGE,
                                        questionIcon);
                                //
                                if(rep == JOptionPane.YES_OPTION) {
                                    insertUpdateDelete("insert into subjectprofile "
                                            + "value ('" + getSubjName.toLowerCase() + "')");
                                    //
                                    int ct = getNumberOfDetails("select * from subjectprofile");
                                    if (ct > 0) {
                                        subjectParaList = 
                                                populateList("select subjectname from subjectprofile "
                                                + "order by subjectname", ct);
                                        //
                                        subjectParaBox.removeAllItems();
                                        for (int pos = 0; pos < subjectParaList.length; pos++) {
                                            subjectParaBox.addItem(
                                                    subjectParaList[pos].toString().toUpperCase());
                                        }
                                    }
                                    //
                                    getQuestionSubject = "";
                                    return;
                                }
                            }
                        }catch(Exception erer){}
                    }
                }
                //
                class addNewClassNameControl implements ActionListener {
                    
                    public void actionPerformed(ActionEvent e) {
                        try {
                            promptLab.setText("Enter New Class Name");
                            String getClassName = JOptionPane.showInputDialog(null, 
                                    promptLab);
                            //
                            if(getNumberOfDetails("select * from classprofile "
                                    + "where classname = '" + getClassName.toLowerCase() + "'") > 0) {
                                //
                                promptLab.setText("Exalt Class Name Already Exist");
                                JOptionPane.showMessageDialog(null, 
                                        promptLab,
                                        getSchoolName.toUpperCase(),
                                        JOptionPane.INFORMATION_MESSAGE,
                                        infoIcon);
                            }
                            else {
                                //
                                promptLab.setText("Does Class Name " + getClassName.toUpperCase() + " "
                                        + "Spelling Accurate Of Choice?");
                                int rep = JOptionPane.showConfirmDialog(null,
                                        promptLab,
                                        getSchoolName.toUpperCase(),
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.QUESTION_MESSAGE,
                                        questionIcon);
                                //
                                if(rep == JOptionPane.YES_OPTION) {
                                    insertUpdateDelete("insert into classprofile "
                                            + "value ('" + getClassName.toLowerCase() + "')");
                                    //
                                    int ct = getNumberOfDetails("select * from classprofile");
                                    if (ct > 0) {
                                        classParaList = 
                                                populateList("select classname from classprofile "
                                                + "order by classname", ct);
                                        //
                                        classParaBox.removeAllItems();
                                        for (int pos = 0; pos < classParaList.length; pos++) {
                                            classParaBox.addItem(
                                                    classParaList[pos].toString().toUpperCase());
                                        }
                                    }
                                    //
                                    getQuestionClass = "";
                                    return;
                                }
                            }
                        }catch(Exception erer){}
                    }
                }
                //
                class addNewSessionNameControl implements ActionListener {
                    
                    public void actionPerformed(ActionEvent e) {
                        try {
                            String sessVal = "20" + year + "/" + (Integer.parseInt(year) + 1);
                            //
                            promptLab.setText("Enter New Session Name. NOTE: CURRENT SESSION/YEAR " + sessVal);
                            String getSessionName = JOptionPane.showInputDialog(null, 
                                    promptLab, sessVal);
                            //
                            if(getNumberOfDetails("select * from sessionprofile "
                                    + "where sessionname = '" + getSessionName.toLowerCase() + "'") > 0) {
                                //
                                promptLab.setText("Exalt Session Name Already Exist");
                                JOptionPane.showMessageDialog(null, 
                                        promptLab,
                                        getSchoolName.toUpperCase(),
                                        JOptionPane.INFORMATION_MESSAGE,
                                        infoIcon);
                            }
                            else {
                                //
                                promptLab.setText("Does Session Name " + getSessionName.toUpperCase() + " "
                                        + "Spelling Accurate Of Choice?");
                                int rep = JOptionPane.showConfirmDialog(null,
                                        promptLab,
                                        getSchoolName.toUpperCase(),
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.QUESTION_MESSAGE,
                                        questionIcon);
                                //
                                if(rep == JOptionPane.YES_OPTION) {
                                    insertUpdateDelete("insert into sessionprofile "
                                            + "value ('" + getSessionName.toLowerCase() + "')");
                                    //
                                    int ct = getNumberOfDetails("select * from sessionprofile");
                                    if (ct > 0) {
                                        sessionParaList = 
                                                populateList("select sessionname from sessionprofile "
                                                + "order by sessionname", ct);
                                        //
                                        sessionParaBox.removeAllItems();
                                        for (int pos = 0; pos < sessionParaList.length; pos++) {
                                            sessionParaBox.addItem(
                                                    sessionParaList[pos].toString().toUpperCase());
                                        }
                                    }
                                    //
                                    getQuestionSession = "";
                                    return;
                                }
                            }
                        }catch(Exception erere){}
                    }
                }
                //
                class displayEntryPage implements ActionListener {
                    //
                    public void actionPerformed(ActionEvent e) {
                        if (!getQuestionClass.equalsIgnoreCase("")
                                && !getQuestionTerm.equalsIgnoreCase("")
                                && !getQuestionSubject.equalsIgnoreCase("")
                                && !questionBatchNumberField.getText().equalsIgnoreCase("")
                                && !getQuestionMode.equalsIgnoreCase("")
                                && !getQuestionSession.equalsIgnoreCase("")) {
                                //
                            if(getQuestionMode.equalsIgnoreCase("objective")) {
                                new createNewObjectiveQuestionControl(); 
                            }
                            else if(getQuestionMode.equalsIgnoreCase("theory")) {
                                new createNewTheoryQuestionControl(); 
                            }
                        }
                        else {
                            promptLab.setText("Select Class, Term, Session "
                                    + "And Subject To Continue");
                            JOptionPane.showMessageDialog(null,
                                    promptLab,
                                    getSchoolName.toUpperCase(),
                                    JOptionPane.INFORMATION_MESSAGE,
                                    infoIcon);
                        }
                    }
                }
                //
                class selectClassControl implements ActionListener {

                    public void actionPerformed(ActionEvent e) {
                        for (int x = 0; x < classParaList.length; x++) {
                            if (x == classParaBox.getSelectedIndex()) {
                                getQuestionClass = classParaList[x].toLowerCase();
                            }
                        }
                    }
                }
                //
                class selectSubjectControl implements ActionListener {

                    public void actionPerformed(ActionEvent e) {
                        for (int x = 0; x < subjectParaList.length; x++) {
                            if (x == subjectParaBox.getSelectedIndex()) {
                                getQuestionSubject = subjectParaList[x].toLowerCase();
                            }
                        }
                        //
                        continueParaButton.setEnabled(true);
                        uploadFromFileButton.setEnabled(true);
                    }
                }
                //
                class selectSessionControl implements ActionListener {

                    public void actionPerformed(ActionEvent e) {
                        for (int x = 0; x < sessionParaList.length; x++) {
                            if (x == sessionParaBox.getSelectedIndex()) {
                                getQuestionSession = sessionParaList[x];
                            }
                        }
                    }
                }
                //
                class selectTermControl implements ActionListener {

                    public void actionPerformed(ActionEvent e) {
                        for (int x = 0; x < termParaList.length; x++) {
                            if (x == termParaBox.getSelectedIndex()) {
                                getQuestionTerm = termParaList[x].toLowerCase();
                            }
                        }
                    }
                }
            }
            //
            class createNewTheoryQuestionControl {
                String[] questionEntryList = {"QUESTION BODY     "};
                JLabel[] questionEntryLab = new JLabel[questionEntryList.length];
                //
                JTextArea questionEntryBodyArea = new JTextArea(10, 30);
                //
                JButton loadQuestionFromFileButton = new JButton("browse question"),
                        //
                        saveQuestionEntryButton = new JButton("Save Entry");
                //
                JLabel[] helpLab = new JLabel[questionEntryList.length];
                //
                JPanel questionBodyPanel = new JPanel();
                //
                public createNewTheoryQuestionControl() {
                    //
                    for(int pos = 0; pos < helpLab.length; pos++) {
                        helpLab[pos] = new JLabel(new ImageIcon("images/help1.png"));
                        helpLab[pos].setCursor(
                                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                        helpLab[pos].addMouseListener(new displayHelpControl(pos));
                    }
                    //
                    formPanel.removeAll();  
                    messageDisplayLab.setText("  Complete Question Form "
                            + "NOTE: Entries Are Case Sensitive");
                    //
                    questionCounter = (getNumberOfDetails("select * from theoryquestionprofile "
                            + "where questionsubject = '" + getQuestionSubject.toLowerCase() + "' "
                            + "and questionclass = '" +  getQuestionClass.toLowerCase() + "' "
                            + "and questionterm = '" +  getQuestionTerm.toLowerCase() + "' "
                            + "and questionsession = '" + getQuestionSession + "' "
                            + "and questionmode = '" + getQuestionMode + "'") + 1);
                    //
                    formPanel.setBorder(
                        BorderFactory.createTitledBorder(null,
                        " " + getQuestionSubject.toUpperCase() + " Question " + questionCounter + " "
                        + "Of Batch " + questionBatchNumberField.getText() + " ",
                        TitledBorder.LEFT, 0, new Font(getFontType, Font.BOLD, 10), 
                        Color.RED));
                    //
                    for(int pos = 0; pos < questionEntryList.length; pos++) {
                        questionEntryLab[pos] = new JLabel(questionEntryList[pos]);
                        questionEntryLab[pos].setForeground(Color.RED);
                        questionEntryLab[pos].setFont(new Font(getFontType, Font.BOLD, 14));
                    }
                    //
                    questionEntryBodyArea.setForeground(Color.BLACK);
                    questionEntryBodyArea.setFont(new Font(getFontType, Font.PLAIN, 16));
                    questionEntryBodyArea.setLineWrap(true);
                    questionEntryBodyArea.setWrapStyleWord(true);
                    //
                    loadQuestionFromFileButton.setForeground(Color.BLACK);
                    loadQuestionFromFileButton.setBackground(Color.WHITE);
                    loadQuestionFromFileButton.setFont(new Font(getFontType, Font.BOLD, 10));
                    //
                    questionBodyPanel.setBackground(themeColor);
                    questionBodyPanel.add(new JScrollPane(questionEntryBodyArea));
                    //
                    JPanel questionPanel = new JPanel();
                    questionPanel.setBackground(themeColor);
                    questionPanel.add(questionEntryLab[0]);
                    questionPanel.add(questionBodyPanel);
                    questionPanel.add(loadQuestionFromFileButton);
                    questionPanel.add(helpLab[0]);
                    //
                    JPanel cFormPanel = new JPanel();
                    cFormPanel.setBackground(themeColor);
                    cFormPanel.setLayout(new BorderLayout());
                    cFormPanel.add(questionPanel, BorderLayout.CENTER);
                    //
                    formPanel.setLayout(new BorderLayout());
                    formPanel.add(questionPanel, BorderLayout.NORTH);
                    formPanel.add(cFormPanel, BorderLayout.WEST);
                    //
                    saveQuestionEntryButton.setForeground(Color.RED);
                    saveQuestionEntryButton.setFont(new Font(getFontType, Font.BOLD, 10));
                    saveQuestionEntryButton.setBackground(Color.WHITE);
                    //
                    buttonPanel.removeAll();
                    buttonPanel.setBackground(Color.WHITE);
                    buttonPanel.setLayout(new FlowLayout());
                    buttonPanel.add(saveQuestionEntryButton);
                    validate();
                    //
                    saveQuestionEntryButton.addActionListener(
                            new submitQuestionInfoControl());
                    loadQuestionFromFileButton.addActionListener(
                            new questionImageBodyControl("question"));
                }
                //
                class submitQuestionInfoControl implements ActionListener {

                    public void actionPerformed(ActionEvent e) {
                        if(questionEntryBodyArea.getText().equalsIgnoreCase("")) {
                            //
                            promptLab.setText("Empty field Detected");
                            JOptionPane.showMessageDialog(null, 
                                    promptLab,
                                    getSchoolName.toUpperCase(),
                                    JOptionPane.INFORMATION_MESSAGE,
                                    infoIcon);
                        }
                        else {
                            //
                            if(getServerIP == null) {
                                //
                                JTextArea msg = new JTextArea("Server IP Format Not Provided. "
                                        + "To Retreive The Correct Server IP Address.... \n\n"
                                        + "1. Click Windows Start Icon\n"
                                        + "2. Type CMD On Search Program And Files And Click CMD On The List\n"
                                        + "3. On The CMD Display Environment, Type ipconfig And Press Enter\n"
                                        + "5. The IP Address Of The Computer (Server) Is "
                                        + "In The Format: 'XXX.XXX.XXX.XXX' Starting With '192.168...'\n"
                                        + "6. Copy And Use As Server IP Address\n\n"
                                        + "Please Restart Server!", 7, 50);
                                //
                                msg.setLineWrap(true);
                                msg.setWrapStyleWord(true);
                                msg.setEditable(false);
                                msg.setBorder(BorderFactory.createEtchedBorder());
                                msg.setFont(new Font(getFontType, Font.PLAIN, 14));
                                msg.setForeground(Color.BLUE);
                                //
                                JOptionPane.showMessageDialog(null, 
                                        new JScrollPane(msg),
                                        getSchoolName.toUpperCase(),
                                        JOptionPane.PLAIN_MESSAGE);
                                //
                                System.exit(0);
                            }
                            //
                            questionEntryBodyArea.setText(
                                    questionEntryBodyArea.getText().replaceAll("C:", "//" + getServerIP + ""));
                            questionEntryBodyArea.setText(
                                    questionEntryBodyArea.getText().replaceAll("'", "\\"));
                            questionEntryBodyArea.setText(
                                    questionEntryBodyArea.getText().replaceAll("\\\\", "\\"));
                            questionEntryBodyArea.setText(
                                    questionEntryBodyArea.getText().replaceAll("'", ""));
                            //
                            int counter = 
                                    (getNumberOfDetails("select * from theoryquestionprofile") + 1),
                                questionID = (getNumberOfDetails("select * from theoryquestionprofile "
                                    + "where questionclass = '" + getQuestionClass.toLowerCase() + "' "
                                    + "and questionterm = '" + getQuestionTerm.toLowerCase() + "' "
                                    + "and questionsession = '" + getQuestionSession + "' "
                                    + "and questionsubject = '" + getQuestionSubject.toLowerCase() + "'") + 1);
                            //
                            insertUpdateDelete("insert into theoryquestionprofile "
                                    + "values ('" + counter + "', "
                                            + "'" + questionID + "', "
                                            + "'" + questionBatchNumberField.getText() + "', "
                                            + "'" + questionEntryBodyArea.getText() + "', "
                                            + "'" + getQuestionClass.toLowerCase() + "', "
                                            + "'" + getQuestionTerm.toLowerCase() + "', "
                                            + "'" + getQuestionSession + "', "
                                            + "'" + getQuestionSubject.toLowerCase() + "', "
                                            + "'" + getQuestionMode + "', "
                                            + "'" + day + " " + letterMonth(Integer.parseInt(mon)) + 
                                                " 20" + year + "')");
                            //new face
                            questionCounter = (getNumberOfDetails("select * from theoryquestionprofile "
                                    + "where questionsubject = '" + getQuestionSubject.toLowerCase() + "' "
                                    + "and questionclass = '" +  getQuestionClass.toLowerCase() + "' "
                                    + "and questionterm = '" +  getQuestionTerm.toLowerCase() + "' "
                                    + "and questionsession = '" + getQuestionSession + "'") + 1);
                            //
                            new createNewTheoryQuestionControl();
                            //
                            promptLab.setText("Question Details Saved.");
                            JOptionPane.showMessageDialog(null, 
                                    promptLab,
                                    getSchoolName.toUpperCase(),
                                    JOptionPane.INFORMATION_MESSAGE,
                                    infoIcon);
                            //
                            new viewQuestion(getQuestionClass, getQuestionTerm,
                                    getQuestionSession, getQuestionSubject, 
                                    questionBatchNumberField.getText(), 
                                    getQuestionMode).start();
                        }
                    }
                }
                //
                void previewImage(String key) {
                    imagePreviewPanel.removeAll();
                    //load staff logo
                    JFileChooser ch = new JFileChooser();
                    ch.setDialogTitle("Image Preview");
                    ch.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    int rep = ch.showOpenDialog(null);
                    if (rep == JFileChooser.CANCEL_OPTION) {
                        promptLab.setText(" Preview Operation Terminated.");
                        JOptionPane.showMessageDialog(null,
                                promptLab,
                                getSchoolName.toUpperCase(),
                                JOptionPane.INFORMATION_MESSAGE,
                                infoIcon);
                    } 
                    else {
                        File dataFile = null;
                        //get file and check for no file selected
                        dataFile = ch.getSelectedFile();
                        if (dataFile.equals(null)
                                || dataFile.getName().equalsIgnoreCase("")) {
                            promptLab.setText(" File Name Not Saved.");
                            JOptionPane.showMessageDialog(null,
                                    promptLab,
                                    getSchoolName.toUpperCase(),
                                    JOptionPane.INFORMATION_MESSAGE,
                                    infoIcon);
                        } 
                        else {
                            String format = "";
                            //change the back slash to front slash
                            for (int x = 0; x < dataFile.getAbsolutePath().length(); x++) {
                                if (dataFile.getAbsolutePath().charAt(x) == '\\') {
                                    format = dataFile.getAbsolutePath().replaceAll("\\\\", "/");
                                }
                            }
                            //
                            format = format.replaceAll("\\\\", "\\");
                            //
                            questionEntryBodyArea.setText(format);
                            //
                            imagePreviewPanel.add(new JLabel(new ImageIcon(format)));
                            validate();
                        }
                    }
                }
            }
            //
            class questionImageBodyControl implements ActionListener {
                String key = "";
                public questionImageBodyControl(String k) {
                    key = k;
                }
                public void actionPerformed(ActionEvent e) {
                    if(getQuestionMode.equalsIgnoreCase("objective")) {
                        createNewObjectiveQuestionControl obj = 
                                new createNewObjectiveQuestionControl();
                        //
                        obj.previewImage(key);
                    }
                    else if(getQuestionMode.equalsIgnoreCase("theory")) {
                        createNewTheoryQuestionControl obj = 
                                new createNewTheoryQuestionControl();
                        //
                        obj.previewImage(key);
                    }
                }
            }
            //
            class displayHelpControl extends MouseAdapter {
                int pos = 0; 
                public displayHelpControl(int p) {pos = p;}
                public void mouseClicked(MouseEvent e) {
                    if(pos == 0) {
                        promptLab.setText("Question In Image Format: "
                                + "Recommended Heigth Should Not Be Greater Than 300px, "
                                + "Variable Length. Be Sure To Upload From Public Document");
                        JOptionPane.showMessageDialog(null, 
                                promptLab,
                                getSchoolName.toUpperCase(),
                                JOptionPane.INFORMATION_MESSAGE,
                                infoIcon);
                    }
                    else {
                        promptLab.setText("Answer Option In Image Format: "
                                + "Recommended Height Should Not Be Greater Than 100px, "
                                + "Variable Length. Be Sure To Upload From Public Document");
                        JOptionPane.showMessageDialog(null, 
                                promptLab,
                                getSchoolName.toUpperCase(),
                                JOptionPane.INFORMATION_MESSAGE,
                                infoIcon);
                    }
                }
            }
            //
            class createNewObjectiveQuestionControl {
                String[] questionEntryList = {"QUESTION BODY     ", "ANSWER OPTION A ", 
                                    "ANSWER OPTION B ", "ANSWER OPTION C ", 
                                    "ANSWER OPTION D ", "ANSWER OPTION E ", 
                                    "CORRECT OPTION   "};
                JLabel[] questionEntryLab = new JLabel[questionEntryList.length];
                //
                JTextArea questionEntryBodyArea = new JTextArea(4, 30);
                //
                JTextField[] answerOptionField = new JTextField[questionEntryList.length - 1];
                //
                JButton loadQuestionFromFileButton = new JButton("browse question"),
                        loadAnswerAFromFileButton = new JButton("browse answer A"),
                        loadAnswerBFromFileButton = new JButton("browse answer B"),
                        loadAnswerCFromFileButton = new JButton("browse answer C"),
                        loadAnswerDFromFileButton = new JButton("browse answer D"),
                        loadAnswerEFromFileButton = new JButton("browse answer E"),
                        //
                        saveQuestionEntryButton = new JButton("Save Entry");
                //
                JLabel[] helpLab = new JLabel[questionEntryList.length - 1];
                //
                JPanel questionBodyPanel = new JPanel(),
                        answerOptionAPanel = new JPanel(),
                        answerOptionBPanel = new JPanel(),
                        answerOptionCPanel = new JPanel(),
                        answerOptionDPanel = new JPanel(),
                        answerOptionEPanel = new JPanel(),
                        correctAnswerOptionPanel = new JPanel();
                //
                JLabel correctAnswerOptionInstrLab = new JLabel("NOTE: "
                        + "Alphabet Only                ");
                //
                JRadioButton textExplanationRadioButton = new JRadioButton("Text Explanation"),
                        imageExplanationRadioButton = new JRadioButton("Image Explanation");
                //
                JTextArea explanationTextArea = new JTextArea(15, 20);
                String imageTextExplanation = "";
                //
                JPanel textImageExplanationRadioButtonPanel = new JPanel();
                //
                public createNewObjectiveQuestionControl() {
                    //
                    for(int pos = 0; pos < helpLab.length; pos++) {
                        helpLab[pos] = new JLabel(new ImageIcon("images/help1.png"));
                        helpLab[pos].setCursor(
                                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                        helpLab[pos].addMouseListener(new displayHelpControl(pos));
                    }
                    //
                    if(getNumberOfDetails("select * from studentexperience") == 0) {
                        insertUpdateDelete("insert into studentexperience "
                                + "values ('nil')");
                    }
                    else {
                        insertUpdateDelete("delete from studentexperience");
                        insertUpdateDelete("insert into studentexperience "
                                + "values ('nil')");
                        //
                        if(enableStudentSectionSummaryCheckBox.isSelected()) {
                            insertUpdateDelete("update studentexperience "
                                    + "set enabledisable = 'true'");
                        }
                        else {
                            insertUpdateDelete("update studentexperience "
                                    + "set enabledisable = 'false'");
                        }
                    }
                    //
                    formPanel.removeAll();
                    messageDisplayLab.setText("  Complete Question Form "
                            + "NOTE: Entries Are Case Sensitive");
                    //
                    questionCounter = (getNumberOfDetails("select * from questionprofile "
                            + "where questionsubject = '" + getQuestionSubject.toLowerCase() + "' "
                            + "and questionclass = '" +  getQuestionClass.toLowerCase() + "' "
                            + "and questionterm = '" +  getQuestionTerm.toLowerCase() + "' "
                            + "and questionsession = '" + getQuestionSession + "' "
                            + "and questionmode = '" + getQuestionMode + "'") + 1);
                    //
                    formPanel.setBorder(
                        BorderFactory.createTitledBorder(null,
                        " " + getQuestionSubject.toUpperCase() + " Question " + questionCounter + " "
                        + "Of Batch " + questionBatchNumberField.getText() + " ",
                        TitledBorder.LEFT, 0, new Font(getFontType, Font.BOLD, 10), 
                        Color.RED));
                    //
                    for(int pos = 0; pos < questionEntryList.length; pos++) {
                        questionEntryLab[pos] = new JLabel(questionEntryList[pos]);
                        questionEntryLab[pos].setForeground(Color.RED);
                        questionEntryLab[pos].setFont(new Font(getFontType, Font.BOLD, 14));
                        //
                        if(pos < questionEntryList.length - 1) {
                            if(pos == questionEntryList.length - 2) {
                                answerOptionField[pos] = new JTextField(5);
                            }
                            else {
                                answerOptionField[pos] = new JTextField(24);
                            }
                            //
                            answerOptionField[pos].setForeground(Color.BLACK);
                            answerOptionField[pos].setFont(new Font(getFontType, Font.PLAIN, 16));
                            answerOptionField[pos].setBorder(BorderFactory.createEtchedBorder());
                        }
                    }
                    //
                    questionEntryBodyArea.setForeground(Color.BLACK);
                    questionEntryBodyArea.setFont(new Font(getFontType, Font.PLAIN, 16));
                    questionEntryBodyArea.setLineWrap(true);
                    questionEntryBodyArea.setWrapStyleWord(true);
                    //
                    loadQuestionFromFileButton.setForeground(Color.BLACK);
                    loadQuestionFromFileButton.setBackground(Color.WHITE);
                    loadQuestionFromFileButton.setFont(new Font(getFontType, Font.BOLD, 10));
                    loadAnswerAFromFileButton.setForeground(Color.BLACK);
                    loadAnswerAFromFileButton.setBackground(Color.WHITE);
                    loadAnswerAFromFileButton.setFont(new Font(getFontType, Font.BOLD, 10));
                    loadAnswerBFromFileButton.setForeground(Color.BLACK);
                    loadAnswerBFromFileButton.setBackground(Color.WHITE);
                    loadAnswerBFromFileButton.setFont(new Font(getFontType, Font.BOLD, 10));
                    loadAnswerCFromFileButton.setForeground(Color.BLACK);
                    loadAnswerCFromFileButton.setBackground(Color.WHITE);
                    loadAnswerCFromFileButton.setFont(new Font(getFontType, Font.BOLD, 10));
                    loadAnswerDFromFileButton.setForeground(Color.BLACK);
                    loadAnswerDFromFileButton.setBackground(Color.WHITE);
                    loadAnswerDFromFileButton.setFont(new Font(getFontType, Font.BOLD, 10));
                    loadAnswerEFromFileButton.setForeground(Color.BLACK);
                    loadAnswerEFromFileButton.setBackground(Color.WHITE);
                    loadAnswerEFromFileButton.setFont(new Font(getFontType, Font.BOLD, 10));
                    //
                    questionBodyPanel.setBackground(themeColor);
                    questionBodyPanel.add(new JScrollPane(questionEntryBodyArea));
                    JPanel questionPanel = new JPanel();
                    questionPanel.setBackground(themeColor);
                    questionPanel.add(questionEntryLab[0]);
                    questionPanel.add(questionBodyPanel);
                    questionPanel.add(loadQuestionFromFileButton);
                    questionPanel.add(helpLab[0]);
                    //
                    answerOptionAPanel.setBackground(themeColor);
                    answerOptionAPanel.add(answerOptionField[0]);
                    JPanel answerAPanel = new JPanel();
                    answerAPanel.setBackground(themeColor);
                    answerAPanel.add(questionEntryLab[1]);
                    answerAPanel.add(answerOptionAPanel);
                    answerAPanel.add(loadAnswerAFromFileButton);
                    answerAPanel.add(helpLab[1]);
                    //
                    answerOptionBPanel.setBackground(themeColor);
                    answerOptionBPanel.add(answerOptionField[1]);
                    JPanel answerBPanel = new JPanel();
                    answerBPanel.setBackground(themeColor);
                    answerBPanel.add(questionEntryLab[2]);
                    answerBPanel.add(answerOptionBPanel);
                    answerBPanel.add(loadAnswerBFromFileButton);
                    answerBPanel.add(helpLab[2]);
                    //
                    answerOptionCPanel.setBackground(themeColor);
                    answerOptionCPanel.add(answerOptionField[2]);
                    JPanel answerCPanel = new JPanel();
                    answerCPanel.setBackground(themeColor);
                    answerCPanel.add(questionEntryLab[3]);
                    answerCPanel.add(answerOptionCPanel);
                    answerCPanel.add(loadAnswerCFromFileButton);
                    answerCPanel.add(helpLab[3]);
                    //
                    answerOptionDPanel.add(answerOptionField[3]);
                    answerOptionDPanel.setBackground(themeColor);
                    JPanel answerDPanel = new JPanel();
                    answerDPanel.setBackground(themeColor);
                    answerDPanel.add(questionEntryLab[4]);
                    answerDPanel.add(answerOptionDPanel);
                    answerDPanel.add(loadAnswerDFromFileButton);
                    answerDPanel.add(helpLab[4]);
                    //
                    answerOptionEPanel.setBackground(themeColor);
                    answerOptionEPanel.add(answerOptionField[4]);
                    JPanel answerEPanel = new JPanel();
                    answerEPanel.setBackground(themeColor);
                    answerEPanel.add(questionEntryLab[5]);
                    answerEPanel.add(answerOptionEPanel);
                    answerEPanel.add(loadAnswerEFromFileButton);
                    answerEPanel.add(helpLab[5]);
                    //
                    correctAnswerOptionInstrLab.setForeground(Color.RED);
                    correctAnswerOptionInstrLab.setFont(new Font(getFontType, Font.BOLD, 10));
                    //
                    correctAnswerOptionPanel.add(answerOptionField[5]);
                    correctAnswerOptionPanel.setBackground(themeColor);
                    JPanel correctAnswerPanel = new JPanel();
                    correctAnswerPanel.setBackground(themeColor);
                    correctAnswerPanel.add(questionEntryLab[6]);
                    correctAnswerPanel.add(correctAnswerOptionPanel);
                    correctAnswerPanel.add(correctAnswerOptionInstrLab);
                    //
                    JPanel centCorrectAnswerPanel = new JPanel();
                    centCorrectAnswerPanel.setBackground(themeColor);
                    centCorrectAnswerPanel.setLayout(new BorderLayout());
                    centCorrectAnswerPanel.add(correctAnswerPanel, BorderLayout.WEST);
                    //
                    JPanel questionOptionSectionPanel = new JPanel();
                    questionOptionSectionPanel.setBackground(themeColor);
                    questionOptionSectionPanel.setLayout(new GridLayout(
                            questionEntryList.length - 1, 1, 0, 0));
                    questionOptionSectionPanel.add(answerAPanel);
                    questionOptionSectionPanel.add(answerBPanel);
                    questionOptionSectionPanel.add(answerCPanel);
                    questionOptionSectionPanel.add(answerDPanel);
                    questionOptionSectionPanel.add(answerEPanel);
                    questionOptionSectionPanel.add(centCorrectAnswerPanel);
                    //
                    JPanel cFormPanel = new JPanel();
                    cFormPanel.setBackground(themeColor);
                    cFormPanel.setLayout(new BorderLayout());
                    cFormPanel.add(questionOptionSectionPanel, BorderLayout.CENTER);
                    //
                    formPanel.setLayout(new BorderLayout());
                    formPanel.add(questionPanel, BorderLayout.NORTH);
                    formPanel.add(cFormPanel, BorderLayout.WEST);
                    //
                    saveQuestionEntryButton.setForeground(Color.RED);
                    saveQuestionEntryButton.setFont(new Font(getFontType, Font.BOLD, 10));
                    saveQuestionEntryButton.setBackground(Color.WHITE);
                    //
                    buttonPanel.removeAll();
                    buttonPanel.setBackground(Color.WHITE);
                    buttonPanel.setLayout(new FlowLayout());
                    buttonPanel.add(saveQuestionEntryButton);
                    //
                    imageExplanationRadioButton.setForeground(Color.BLACK);
                    imageExplanationRadioButton.setBackground(Color.WHITE);
                    imageExplanationRadioButton.setFont(new Font(getFontType, Font.PLAIN, 12));
                    textExplanationRadioButton.setForeground(Color.BLACK);
                    textExplanationRadioButton.setFont(new Font(getFontType, Font.PLAIN, 12));
                    textExplanationRadioButton.setBackground(Color.WHITE);
                    //
                    textImageExplanationRadioButtonPanel.setBackground(Color.WHITE);
                    textImageExplanationRadioButtonPanel.setBorder(BorderFactory.createEtchedBorder());
                    textImageExplanationRadioButtonPanel.add(textExplanationRadioButton);
                    textImageExplanationRadioButtonPanel.add(imageExplanationRadioButton);
                    textImageExplanationRadioButtonPanel.setBorder(
                            BorderFactory.createTitledBorder(null,
                            " Question Explanation Section ",
                            TitledBorder.LEFT, 0, new Font(getFontType, Font.BOLD, 10), 
                            Color.BLUE));
                    //
                    if(enableStudentSectionSummaryCheckBox.isSelected()) {
                        centImagePreviewPanel.removeAll();
                        centImagePreviewPanel.setLayout(new BorderLayout());
                        //
                        centImagePreviewPanel.add(textImageExplanationRadioButtonPanel, BorderLayout.NORTH);
                        centImagePreviewPanel.add(imagePreviewPanel, BorderLayout.CENTER);
                    }
                    //
                    validate();
                    //
                    saveQuestionEntryButton.addActionListener(
                            new submitQuestionInfoControl());
                    loadQuestionFromFileButton.addActionListener(
                            new questionImageBodyControl("question"));
                    loadAnswerAFromFileButton.addActionListener(
                            new questionImageBodyControl("optiona"));
                    loadAnswerBFromFileButton.addActionListener(
                            new questionImageBodyControl("optionb"));
                    loadAnswerCFromFileButton.addActionListener(
                            new questionImageBodyControl("optionc"));
                    loadAnswerDFromFileButton.addActionListener(
                            new questionImageBodyControl("optiond"));
                    loadAnswerEFromFileButton.addActionListener(
                            new questionImageBodyControl("optione"));
                    imageExplanationRadioButton.addActionListener(new imageExplainControl());
                    textExplanationRadioButton.addActionListener(new textExplainControl());
                }
                //
                void previewImage(String key) {
                    imagePreviewPanel.removeAll();
                    //load staff logo
                    JFileChooser ch = new JFileChooser();
                    ch.setDialogTitle("Image Preview");
                    ch.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    int rep = ch.showOpenDialog(null);
                    if (rep == JFileChooser.CANCEL_OPTION) {
                        promptLab.setText(" Preview Operation Terminated.");
                        JOptionPane.showMessageDialog(null,
                                promptLab,
                                getSchoolName.toUpperCase(),
                                JOptionPane.INFORMATION_MESSAGE,
                                infoIcon);
                    } 
                    else {
                        File dataFile = null;
                        //get file and check for no file selected
                        dataFile = ch.getSelectedFile();
                        if (dataFile.equals(null)
                                || dataFile.getName().equalsIgnoreCase("")) {
                            promptLab.setText(" File Name Not Saved.");
                            JOptionPane.showMessageDialog(null,
                                    promptLab,
                                    getSchoolName.toUpperCase(),
                                    JOptionPane.INFORMATION_MESSAGE,
                                    infoIcon);
                        } 
                        else {
                            String format = "";
                            //change the back slash to front slash
                            for (int x = 0; x < dataFile.getAbsolutePath().length(); x++) {
                                if (dataFile.getAbsolutePath().charAt(x) == '\\') {
                                    format = dataFile.getAbsolutePath().replaceAll("\\\\", "/");
                                }
                            }
                            //
                            format = format.replaceAll("\\\\", "\\");
                            //
                            if(key.equalsIgnoreCase("optiona")) {
                                answerOptionField[0].setText(format);
                            }
                            else if(key.equalsIgnoreCase("optionb")) {
                                answerOptionField[1].setText(format);
                            }
                            else if(key.equalsIgnoreCase("optionc")) {
                                answerOptionField[2].setText(format);
                            }
                            else if(key.equalsIgnoreCase("optiond")) {
                                answerOptionField[3].setText(format);
                            }
                            else if(key.equalsIgnoreCase("optione")) {
                                answerOptionField[4].setText(format);
                            }
                            else {
                                questionEntryBodyArea.setText(format);
                            }
                            //
                            imagePreviewPanel.add(new JLabel(new ImageIcon(format)));
                            validate();
                        }
                    }
                }
                //
                class textExplainControl implements ActionListener {
                    //
                    public void actionPerformed(ActionEvent e) {
                        imagePreviewPanel.removeAll();
                        //
                        explanationTextArea.setForeground(Color.BLACK);
                        explanationTextArea.setFont(new Font(getFontType, Font.PLAIN, 14));
                        explanationTextArea.setLineWrap(true);
                        explanationTextArea.setWrapStyleWord(true);
                        //
                        imageTextExplanation = "text";
                        imageExplanationRadioButton.setSelected(false);
                        //
                        imagePreviewPanel.add(new JScrollPane(explanationTextArea));
                        validate();
                    }
                }
                //
                class imageExplainControl implements ActionListener {
                    //
                    JButton browseButton = new JButton("Browse");
                    //
                    public void actionPerformed(ActionEvent e) {
                        //
                        browseButton.setFont(new Font(getFontType, Font.BOLD, 10));
                        browseButton.setForeground(Color.RED);
                        browseButton.setBackground(Color.WHITE);
                        //
                        imageTextExplanation = "image";
                        textExplanationRadioButton.setSelected(false);
                        //
                        textImageExplanationRadioButtonPanel.removeAll();
                        textImageExplanationRadioButtonPanel.add(textExplanationRadioButton);
                        textImageExplanationRadioButtonPanel.add(browseButton);
                        validate();
                        //
                        browseButton.addActionListener(new loadImage());
                    }
                    //
                    class loadImage implements ActionListener {
                        
                        public void actionPerformed(ActionEvent e) {
                            imagePreviewPanel.removeAll();
                            //load staff logo
                            JFileChooser ch = new JFileChooser();
                            ch.setDialogTitle("Image Preview");
                            ch.setFileSelectionMode(JFileChooser.FILES_ONLY);
                            int rep = ch.showOpenDialog(null);
                            if (rep == JFileChooser.CANCEL_OPTION) {
                                promptLab.setText(" Preview Operation Terminated.");
                                JOptionPane.showMessageDialog(null,
                                        promptLab,
                                        getSchoolName.toUpperCase(),
                                        JOptionPane.INFORMATION_MESSAGE,
                                        infoIcon);
                            } 
                            else {
                                File dataFile = null;
                                //get file and check for no file selected
                                dataFile = ch.getSelectedFile();
                                if (dataFile.equals(null)
                                        || dataFile.getName().equalsIgnoreCase("")) {
                                    promptLab.setText(" File Name Not Saved.");
                                    JOptionPane.showMessageDialog(null,
                                            promptLab,
                                            getSchoolName.toUpperCase(),
                                            JOptionPane.INFORMATION_MESSAGE,
                                            infoIcon);
                                } 
                                else {
                                    String format = "";
                                    //change the back slash to front slash
                                    for (int x = 0; x < dataFile.getAbsolutePath().length(); x++) {
                                        if (dataFile.getAbsolutePath().charAt(x) == '\\') {
                                            format = dataFile.getAbsolutePath().replaceAll("\\\\", "/");
                                        }
                                    }
                                    //
                                    format = format.replaceAll("\\\\", "\\");
                                    //
                                    imageTextExplanation = format;
                                    imagePreviewPanel.add(new JLabel(new ImageIcon(format)));
                                    validate();
                                }
                            }
                        }
                    }
                }
                //
                class submitQuestionInfoControl implements ActionListener {

                    public void actionPerformed(ActionEvent e) {
                        if(questionEntryBodyArea.getText().equalsIgnoreCase("") || 
                            answerOptionField[0].getText().equalsIgnoreCase("") ||
                            answerOptionField[1].getText().equalsIgnoreCase("") ||
                            answerOptionField[2].getText().equalsIgnoreCase("") ||
                            answerOptionField[3].getText().equalsIgnoreCase("") ||
                            answerOptionField[4].getText().equalsIgnoreCase("") ||
                            answerOptionField[5].getText().equalsIgnoreCase("")) {
                            //
                            promptLab.setText("Empty field Detected");
                            JOptionPane.showMessageDialog(null, 
                                    promptLab,
                                    getSchoolName.toUpperCase(),
                                    JOptionPane.INFORMATION_MESSAGE,
                                    infoIcon);
                        }
                        else if(!answerOptionField[5].getText().equalsIgnoreCase("a") &&
                            !answerOptionField[5].getText().equalsIgnoreCase("b") &&
                            !answerOptionField[5].getText().equalsIgnoreCase("c") &&
                            !answerOptionField[5].getText().equalsIgnoreCase("d") &&
                            !answerOptionField[5].getText().equalsIgnoreCase("e")) {
                            //
                            promptLab.setText("Answer Option Entered Not Valid");
                            JOptionPane.showMessageDialog(null, 
                                    promptLab,
                                    getSchoolName.toUpperCase(),
                                    JOptionPane.WARNING_MESSAGE,
                                    warningIcon);
                            answerOptionField[5].setText("");
                        }
                        else if(answerOptionField[5].getText().length() > 1) {
                            //
                            promptLab.setText("Correct Option Field Can Only Take One Letter.");
                            JOptionPane.showMessageDialog(null, 
                                    promptLab,
                                    getSchoolName.toUpperCase(),
                                    JOptionPane.ERROR_MESSAGE,
                                    errorIcon);
                            answerOptionField[5].setText("");
                        }
                        else {
                            //
                            if(getServerIP == null) {
                                //
                                JTextArea msg = new JTextArea("Server IP Format Not Provided. "
                                        + "To Retreive The Correct Server IP Address.... \n\n"
                                        + "1. Click Windows Start Icon\n"
                                        + "2. Type CMD On Search Program And Files And Click CMD On The List\n"
                                        + "3. On The CMD Display Environment, Type ipconfig And Press Enter\n"
                                        + "5. The IP Address Of The Computer (Server) Is "
                                        + "In The Format: 'XXX.XXX.XXX.XXX' Starting With '192.168...'\n"
                                        + "6. Copy And Use As Server IP Address\n\n"
                                        + "Please Restart Server!", 7, 50);
                                //
                                msg.setLineWrap(true);
                                msg.setWrapStyleWord(true);
                                msg.setEditable(false);
                                msg.setBorder(BorderFactory.createEtchedBorder());
                                msg.setFont(new Font(getFontType, Font.PLAIN, 14));
                                msg.setForeground(Color.BLUE);
                                //
                                JOptionPane.showMessageDialog(null, 
                                        new JScrollPane(msg),
                                        getSchoolName.toUpperCase(),
                                        JOptionPane.PLAIN_MESSAGE);
                                //
                                System.exit(0);
                            }
                            //
                            questionEntryBodyArea.setText(
                                    questionEntryBodyArea.getText().replaceAll("C:", "//" + getServerIP + ""));
                            answerOptionField[0].setText(
                                    answerOptionField[0].getText().replaceAll("C:", "//" + getServerIP + ""));
                            answerOptionField[1].setText(
                                    answerOptionField[1].getText().replaceAll("C:", "//" + getServerIP + ""));
                            answerOptionField[2].setText(
                                    answerOptionField[2].getText().replaceAll("C:", "//" + getServerIP + ""));
                            answerOptionField[3].setText(
                                    answerOptionField[3].getText().replaceAll("C:", "//" + getServerIP + ""));
                            answerOptionField[4].setText(
                                    answerOptionField[4].getText().replaceAll("C:", "//" + getServerIP + ""));
                            answerOptionField[5].setText(
                                    answerOptionField[5].getText().replaceAll("C:", "//" + getServerIP + ""));
                            //
                            questionEntryBodyArea.setText(
                                    questionEntryBodyArea.getText().replaceAll("'", "\\"));
                            answerOptionField[0].setText(
                                    answerOptionField[0].getText().replaceAll("'", "\\"));
                            answerOptionField[1].setText(
                                    answerOptionField[1].getText().replaceAll("'", "\\"));
                            answerOptionField[2].setText(
                                    answerOptionField[2].getText().replaceAll("'", "\\"));
                            answerOptionField[3].setText(
                                    answerOptionField[3].getText().replaceAll("'", "\\"));
                            answerOptionField[4].setText(
                                    answerOptionField[4].getText().replaceAll("'", "\\"));
                            answerOptionField[5].setText(
                                    answerOptionField[5].getText().replaceAll("'", "\\"));
                            //
                            questionEntryBodyArea.setText(
                                    questionEntryBodyArea.getText().replaceAll("\\\\", "\\"));
                            answerOptionField[0].setText(
                                    answerOptionField[0].getText().replaceAll("\\\\", "\\"));
                            answerOptionField[1].setText(
                                    answerOptionField[1].getText().replaceAll("\\\\", "\\"));
                            answerOptionField[2].setText(
                                    answerOptionField[2].getText().replaceAll("\\\\", "\\"));
                            answerOptionField[3].setText(
                                    answerOptionField[3].getText().replaceAll("\\\\", "\\"));
                            answerOptionField[4].setText(
                                    answerOptionField[4].getText().replaceAll("\\\\", "\\"));
                            answerOptionField[5].setText(
                                    answerOptionField[5].getText().replaceAll("\\\\", "\\"));
                            //
                            questionEntryBodyArea.setText(
                                    questionEntryBodyArea.getText().replaceAll("'", ""));
                            answerOptionField[0].setText(
                                    answerOptionField[0].getText().replaceAll("'", ""));
                            answerOptionField[1].setText(
                                    answerOptionField[1].getText().replaceAll("'", ""));
                            answerOptionField[2].setText(
                                    answerOptionField[2].getText().replaceAll("'", ""));
                            answerOptionField[3].setText(
                                    answerOptionField[3].getText().replaceAll("'", ""));
                            answerOptionField[4].setText(
                                    answerOptionField[4].getText().replaceAll("'", ""));
                            answerOptionField[5].setText(
                                    answerOptionField[5].getText().replaceAll("'", ""));
                            //
                            int counter = 
                                    (getNumberOfDetails("select * from questionprofile") + 1),
                                questionID = (getNumberOfDetails("select * from questionprofile "
                                    + "where questionclass = '" + getQuestionClass.toLowerCase() + "' "
                                    + "and questionterm = '" + getQuestionTerm.toLowerCase() + "' "
                                    + "and questionsession = '" + getQuestionSession + "' "
                                    + "and questionsubject = '" + getQuestionSubject.toLowerCase() + "'") + 1);
                            //
                            insertUpdateDelete("insert into questionprofile "
                                    + "values ('" + counter + "', "
                                            + "'" + questionID + "', "
                                            + "'" + questionBatchNumberField.getText() + "', "
                                            + "'" + questionEntryBodyArea.getText() + "', "
                                            + "'" + answerOptionField[0].getText() + "', "
                                            + "'" + answerOptionField[1].getText() + "', "
                                            + "'" + answerOptionField[2].getText() + "', "
                                            + "'" + answerOptionField[3].getText() + "', "
                                            + "'" + answerOptionField[4].getText() + "', "
                                            + "'" + answerOptionField[5].getText() + "', "
                                            + "'" + getQuestionClass.toLowerCase() + "', "
                                            + "'" + getQuestionTerm.toLowerCase() + "', "
                                            + "'" + getQuestionSession + "', "
                                            + "'" + getQuestionSubject.toLowerCase() + "', "
                                            + "'" + getQuestionMode + "', "
                                            + "'" + day + " " + letterMonth(Integer.parseInt(mon)) + 
                                                " 20" + year + "')");
                            //new face
                            questionCounter = (getNumberOfDetails("select * from questionprofile "
                                    + "where questionsubject = '" + getQuestionSubject.toLowerCase() + "' "
                                    + "and questionclass = '" +  getQuestionClass.toLowerCase() + "' "
                                    + "and questionterm = '" +  getQuestionTerm.toLowerCase() + "' "
                                    + "and questionsession = '" + getQuestionSession + "'") + 1);
                            //
                            counter = (getNumberOfDetails("select * from questionexplanation") + 1);
                            //
                            explanationTextArea.setText(
                                    explanationTextArea.getText().replaceAll("C:", "//" + getServerIP + ""));
                            explanationTextArea.setText(
                                    explanationTextArea.getText().replaceAll("'", "\\"));
                            explanationTextArea.setText(
                                    explanationTextArea.getText().replaceAll("\\\\", "\\"));
                            //
                            insertUpdateDelete("insert into questionexplanation "
                                + "values ('" + counter + "', "
                                        + "'" + getQuestionClass.toLowerCase() + "', "
                                        + "'" + getQuestionTerm.toLowerCase() + "', "
                                        + "'" + getQuestionSession + "', "
                                        + "'" + getQuestionSubject.toLowerCase() + "', "
                                        + "'" + questionEntryBodyArea.getText() + "', "
                                        + "'" + explanationTextArea.getText() + "', "
                                        + "'" + getQuestionMode + "')");
                            //
                            new createNewObjectiveQuestionControl();
                            //
                            promptLab.setText("Question Details Saved.");
                            JOptionPane.showMessageDialog(null, 
                                    promptLab,
                                    getSchoolName.toUpperCase(),
                                    JOptionPane.INFORMATION_MESSAGE,
                                    infoIcon);
                            //
                            new viewQuestion(getQuestionClass, getQuestionTerm,
                                    getQuestionSession, getQuestionSubject, 
                                    questionBatchNumberField.getText(), 
                                    getQuestionMode).start();
                        }
                    }
                }
            }
            //
            class viewQuestionModeControl implements ActionListener {

                public void actionPerformed(ActionEvent e) {
                    for (int x = 0; x < viewQuestionModeList.length; x++) {
                        if (x == questionModeBox.getSelectedIndex()) {
                            getQuestionMode = viewQuestionModeList[x].toLowerCase();
                        }
                    }
                }
            }
            //
            class viewQuestionYearControl implements ActionListener {

                public void actionPerformed(ActionEvent e) {
                    for (int x = 0; x < viewQuestionYearList.length; x++) {
                        if (x == yearBox.getSelectedIndex()) {
                            getQuestionSession = viewQuestionYearList[x].toLowerCase();
                        }
                    }
                }
            }
            //
            class viewQuestionSubjectControl implements ActionListener {

                public void actionPerformed(ActionEvent e) {
                    for (int x = 0; x < viewQuestionSubjectList.length; x++) {
                        if (x == subjectBox.getSelectedIndex()) {
                            getQuestionSubject = viewQuestionSubjectList[x].toLowerCase();
                        }
                    }
                }
            }
            //
            class viewQuestionClassControl implements ActionListener {

                public void actionPerformed(ActionEvent e) {
                    for (int x = 0; x < viewQuestionClassList.length; x++) {
                        if (x == classBox.getSelectedIndex()) {
                            getQuestionClass = viewQuestionClassList[x].toLowerCase();
                        }
                    }
                }
            }
            //
            class viewQuestionTermControl implements ActionListener {

                public void actionPerformed(ActionEvent e) {
                    for (int x = 0; x < viewQuestionTermList.length; x++) {
                        if (x == termBox.getSelectedIndex()) {
                            getQuestionTerm = viewQuestionTermList[x].toLowerCase();
                        }
                    }
                }
            }
            //
            class viewQuestionBatchControl implements ActionListener {

                public void actionPerformed(ActionEvent e) {
                    for (int x = 0; x < viewQuestionBatchList.length; x++) {
                        if (x == batchBox.getSelectedIndex()) {
                            getQuestionBatch = viewQuestionBatchList[x];
                        }
                    }
                }
            }
            //
            class viewQuestion extends Thread {

                String questionClass = "",
                        questionTerm = "",
                        questionYear = "",
                        questionBatch = "",
                        questionMode = "",
                        questionSubject = "";
                //
                public viewQuestion(String cl, String tm,
                        String yr, String sb, String bt, 
                        String md) {
                    //
                    questionClass = cl;
                    questionTerm = tm;
                    questionSubject = sb;
                    questionBatch = bt;
                    questionYear = yr;
                    questionMode = md;
                }
                //
                public void run() {
                    JPanel subjectBoxPanel = new JPanel();
                    subjectBoxPanel.setBackground(themeColor);
                    //
                    JPanel headerLeftPanel = new JPanel();
                    headerLeftPanel.setBackground(Color.LIGHT_GRAY);
                    //
                    if (!questionYear.equalsIgnoreCase("")
                            && !questionClass.equalsIgnoreCase("")
                            && !questionTerm.equalsIgnoreCase("")
                            && !questionTerm.equalsIgnoreCase("")
                            && !questionMode.equalsIgnoreCase("")
                            && !questionBatch.equalsIgnoreCase("")
                            && !questionSubject.equalsIgnoreCase("")) {
                        //
                        if(questionMode.equalsIgnoreCase("objective")) {
                            resCount = getNumberOfDetails("select * from questionprofile "
                                + "where questionsession = '" + questionYear + "' "
                                + "and questionterm = '" + questionTerm + "' "
                                + "and questionclass = '" + questionClass + "' "
                                + "and questionsubject = '" + questionSubject + "' "
                                + "and questionmode = '" + questionMode + "' "
                                + "and questionbatchnumber = '" + questionBatch + "'");
                        }
                        else if(questionMode.equalsIgnoreCase("theory")) {
                            resCount = getNumberOfDetails("select * from theoryquestionprofile "
                                + "where questionsession = '" + questionYear + "' "
                                + "and questionterm = '" + questionTerm + "' "
                                + "and questionclass = '" + questionClass + "' "
                                + "and questionsubject = '" + questionSubject + "' "
                                + "and questionmode = '" + questionMode + "' "
                                + "and questionbatchnumber = '" + questionBatch + "'");
                        }
                    }
                    //
                    subjectBoxPanel.setLayout(new GridLayout(resCount, 1, 0, 0));
                    //
                    printExcelText = "";
                    //
                    JPanel headerPanel = new JPanel();
                    headerPanel.setBackground(Color.LIGHT_GRAY);
                    //
                    if (resCount > 0) {
                        //
                        if(questionMode.equalsIgnoreCase("objective")) {
                            resList = populateList("select counter from questionprofile "
                                    + "where questionsession = '" + questionYear + "' "
                                    + "and questionterm = '" + questionTerm + "' "
                                    + "and questionclass = '" + questionClass + "' "
                                    + "and questionsubject = '" + questionSubject + "' "
                                    + "and questionmode = '" + questionMode + "' "
                                    + "and questionbatchnumber = '" + questionBatch + "' "
                                    + "order by questionid", resCount);
                            //
                            JTextField questionIDLab = new JTextField("ID", 3);
                            questionIDLab.setForeground(Color.BLUE);
                            questionIDLab.setFont(new Font(getFontType, Font.BOLD, 14));
                            questionIDLab.setBorder(BorderFactory.createEmptyBorder());
                            questionIDLab.setBackground(Color.LIGHT_GRAY);
                            questionIDLab.setEditable(false);
                            //
                            JTextField questionHeaderLab = new JTextField("QUESTION BODY", 24);
                            questionHeaderLab.setForeground(Color.BLUE);
                            questionHeaderLab.setFont(new Font(getFontType, Font.BOLD, 14));
                            questionHeaderLab.setBorder(BorderFactory.createEmptyBorder());
                            questionHeaderLab.setBackground(Color.LIGHT_GRAY);
                            questionHeaderLab.setEditable(false);
                            //
                            JTextField option1Lab = new JTextField("OPTION A", 7);
                            option1Lab.setForeground(Color.BLUE);
                            option1Lab.setFont(new Font(getFontType, Font.BOLD, 14));
                            option1Lab.setBorder(BorderFactory.createEmptyBorder());
                            option1Lab.setBackground(Color.LIGHT_GRAY);
                            option1Lab.setEditable(false);
                            //
                            JTextField option2Lab = new JTextField("OPTION B", 8);
                            option2Lab.setForeground(Color.BLUE);
                            option2Lab.setFont(new Font(getFontType, Font.BOLD, 14));
                            option2Lab.setBorder(BorderFactory.createEmptyBorder());
                            option2Lab.setBackground(Color.LIGHT_GRAY);
                            option2Lab.setEditable(false);
                            //
                            JTextField option3Lab = new JTextField("OPTION C", 7);
                            option3Lab.setForeground(Color.BLUE);
                            option3Lab.setFont(new Font(getFontType, Font.BOLD, 14));
                            option3Lab.setBorder(BorderFactory.createEmptyBorder());
                            option3Lab.setBackground(Color.LIGHT_GRAY);
                            option3Lab.setEditable(false);
                            //
                            JTextField option4Lab = new JTextField("OPTION D", 7);
                            option4Lab.setForeground(Color.BLUE);
                            option4Lab.setFont(new Font(getFontType, Font.BOLD, 14));
                            option4Lab.setBorder(BorderFactory.createEmptyBorder());
                            option4Lab.setBackground(Color.LIGHT_GRAY);
                            option4Lab.setEditable(false);
                            //
                            JTextField option5Lab = new JTextField("OPTION E", 7);
                            option5Lab.setForeground(Color.BLUE);
                            option5Lab.setFont(new Font(getFontType, Font.BOLD, 14));
                            option5Lab.setBorder(BorderFactory.createEmptyBorder());
                            option5Lab.setBackground(Color.LIGHT_GRAY);
                            option5Lab.setEditable(false);
                            //
                            JTextField correctLab = new JTextField("CORR", 4);
                            correctLab.setForeground(Color.BLUE);
                            correctLab.setFont(new Font(getFontType, Font.BOLD, 14));
                            correctLab.setBorder(BorderFactory.createEmptyBorder());
                            correctLab.setBackground(Color.LIGHT_GRAY);
                            correctLab.setEditable(false);
                            //
                            JTextField batchLab = new JTextField("BATCH", 4);
                            batchLab.setForeground(Color.BLUE);
                            batchLab.setFont(new Font(getFontType, Font.BOLD, 14));
                            batchLab.setBorder(BorderFactory.createEmptyBorder());
                            batchLab.setBackground(Color.LIGHT_GRAY);
                            batchLab.setEditable(false);
                            //
                            JTextField dateLab = new JTextField("DATE", 10);
                            dateLab.setForeground(Color.BLUE);
                            dateLab.setFont(new Font(getFontType, Font.BOLD, 14));
                            dateLab.setBorder(BorderFactory.createEmptyBorder());
                            dateLab.setBackground(Color.LIGHT_GRAY);
                            dateLab.setEditable(false);
                            //
                            headerPanel.add(new JLabel("         "));
                            headerPanel.add(questionIDLab);
                            headerPanel.add(questionHeaderLab);
                            headerPanel.add(option1Lab);
                            headerPanel.add(option2Lab);
                            headerPanel.add(option3Lab);
                            headerPanel.add(option4Lab);
                            headerPanel.add(option5Lab);
                            headerPanel.add(correctLab);
                            headerPanel.add(batchLab);
                            headerPanel.add(dateLab);
                            //
                            printExcelText += questionIDLab.getText() + "\t";
                            printExcelText += questionHeaderLab.getText() + "\t";
                            printExcelText += option1Lab.getText() + "\t";
                            printExcelText += option2Lab.getText() + "\t";
                            printExcelText += option3Lab.getText() + "\t";
                            printExcelText += option4Lab.getText() + "\t";
                            printExcelText += option5Lab.getText() + "\t";
                            printExcelText += correctLab.getText() + "\t";
                            printExcelText += batchLab.getText() + "\t";
                            printExcelText += dateLab.getText() + "\n";
                            //
                            headerLeftPanel.setLayout(new BorderLayout());
                            headerLeftPanel.add(headerPanel, BorderLayout.WEST);
                            //
                            questionIDField = new JTextField[resCount];
                            questionAnswerOption1Field = new JTextField[resCount];
                            questionAnswerOption2Field = new JTextField[resCount];
                            questionAnswerOption3Field = new JTextField[resCount];
                            questionAnswerOption4Field = new JTextField[resCount];
                            questionAnswerOption5Field = new JTextField[resCount];
                            answerCorrectOptionField = new JTextField[resCount];
                            questionBatchField = new JTextField[resCount];
                            JTextField[] dateField = new JTextField[resCount];
                            bodyText = new String[resCount][6];
                            //
                            questionBodyArea = new JTextArea[resCount];
                            //
                            questionCheckBox = new JCheckBox[resCount];
                            questionUpdateButton = new JButton[resCount];
                            //
                            for (int pos = 0; pos < resCount; pos++) {
                                JPanel onePanel = new JPanel();
                                //
                                commQuestionLab.setText(" Uploading " + (pos + 1) + " Records From "
                                        + "Selected Parameters ...Please Wait!");
                                //
                                questionCheckBox[pos] = new JCheckBox();
                                questionCheckBox[pos].setBackground(themeColor);
                                questionCheckBox[pos].addItemListener(new enableField(pos));
                                onePanel.add(questionCheckBox[pos]);
                                //
                                questionIDField[pos] = new JTextField(
                                        getOneDetail("select questionid from questionprofile "
                                        + "where counter = '" + resList[pos] + "'"), 3);
                                questionIDField[pos].setForeground(Color.BLACK);
                                questionIDField[pos].setBorder(BorderFactory.createEmptyBorder());
                                questionIDField[pos].setFont(new Font(getFontType, Font.PLAIN, 12));
                                questionIDField[pos].setEditable(false);
                                onePanel.add(questionIDField[pos]);
                                //
                                printExcelText += questionIDField[pos].getText() + "\t";
                                //
                                bodyText[pos][0] = 
                                        getOneDetail("select questionbody from questionprofile "
                                        + "where counter = '" + resList[pos] + "'");
                                questionBodyArea[pos] = new JTextArea(bodyText[pos][0], 4, 26);
                                questionBodyArea[pos].setForeground(Color.BLACK);
                                questionBodyArea[pos].setLineWrap(true);
                                questionBodyArea[pos].setWrapStyleWord(true);
                                questionBodyArea[pos].setFont(new Font(getFontType, Font.PLAIN, 12));
                                questionBodyArea[pos].setEditable(false);
                                onePanel.add(new JScrollPane(questionBodyArea[pos]));
                                //
                                printExcelText += questionBodyArea[pos].getText() + "\t";
                                //
                                bodyText[pos][1] = 
                                        getOneDetail("select answeroptiona from questionprofile "
                                        + "where counter = '" + resList[pos] + "'");
                                //
                                printExcelText += bodyText[pos][1] + "\t";
                                //
                                questionAnswerOption1Field[pos] = new JTextField(bodyText[pos][1], 8);
                                questionAnswerOption1Field[pos].setForeground(Color.BLACK);
                                questionAnswerOption1Field[pos].setBorder(BorderFactory.createEmptyBorder());
                                questionAnswerOption1Field[pos].setFont(new Font(getFontType, Font.PLAIN, 12));
                                questionAnswerOption1Field[pos].setEditable(false);
                                questionAnswerOption1Field[pos].setToolTipText(
                                        bodyText[pos][1].toUpperCase());
                                if(bodyText[pos][1].startsWith("//" + getServerIP + "/")) {
                                    questionAnswerOption1Field[pos].setText(bodyText[pos][1].substring(0, 3) + "../" + 
                                            bodyText[pos][1].substring(bodyText[pos][1].lastIndexOf("/") + 1));
                                }
                                onePanel.add(questionAnswerOption1Field[pos]);
                                //
                                bodyText[pos][2] = 
                                        getOneDetail("select answeroptionb from questionprofile "
                                        + "where counter = '" + resList[pos] + "'");
                                //
                                printExcelText += bodyText[pos][2] + "\t";
                                //
                                questionAnswerOption2Field[pos] = new JTextField(bodyText[pos][2], 8);
                                questionAnswerOption2Field[pos].setForeground(Color.BLACK);
                                questionAnswerOption2Field[pos].setBorder(BorderFactory.createEmptyBorder());
                                questionAnswerOption2Field[pos].setFont(new Font(getFontType, Font.PLAIN, 12));
                                questionAnswerOption2Field[pos].setEditable(false);
                                questionAnswerOption2Field[pos].setToolTipText(
                                        bodyText[pos][2].toUpperCase());
                                if(bodyText[pos][2].startsWith("//" + getServerIP + "/")) {
                                    questionAnswerOption2Field[pos].setText(bodyText[pos][2].substring(0, 3) + "../" + 
                                            bodyText[pos][2].substring(bodyText[pos][2].lastIndexOf("/") + 1));
                                }
                                onePanel.add(questionAnswerOption2Field[pos]);
                                //
                                bodyText[pos][3] = 
                                        getOneDetail("select answeroptionc from questionprofile "
                                        + "where counter = '" + resList[pos] + "'");
                                //
                                printExcelText += bodyText[pos][3] + "\t";
                                //
                                questionAnswerOption3Field[pos] = new JTextField(bodyText[pos][3], 8);
                                questionAnswerOption3Field[pos].setForeground(Color.BLACK);
                                questionAnswerOption3Field[pos].setBorder(BorderFactory.createEmptyBorder());
                                questionAnswerOption3Field[pos].setFont(new Font(getFontType, Font.PLAIN, 12));
                                questionAnswerOption3Field[pos].setEditable(false);
                                questionAnswerOption3Field[pos].setToolTipText(
                                        bodyText[pos][3].toUpperCase());
                                if(bodyText[pos][3].startsWith("//" + getServerIP + "/")) {
                                    questionAnswerOption3Field[pos].setText(bodyText[pos][3].substring(0, 3) + "../" + 
                                            bodyText[pos][3].substring(bodyText[pos][3].lastIndexOf("/") + 1));
                                }
                                onePanel.add(questionAnswerOption3Field[pos]);
                                //
                                bodyText[pos][4] = 
                                        getOneDetail("select answeroptiond from questionprofile "
                                        + "where counter = '" + resList[pos] + "'");
                                //
                                printExcelText += bodyText[pos][4] + "\t";
                                //
                                questionAnswerOption4Field[pos] = new JTextField(bodyText[pos][4], 8);
                                questionAnswerOption4Field[pos].setForeground(Color.BLACK);
                                questionAnswerOption4Field[pos].setBorder(BorderFactory.createEmptyBorder());
                                questionAnswerOption4Field[pos].setFont(new Font(getFontType, Font.PLAIN, 12));
                                questionAnswerOption4Field[pos].setEditable(false);
                                questionAnswerOption4Field[pos].setToolTipText(bodyText[pos][4].toUpperCase());
                                if(bodyText[pos][4].startsWith("//" + getServerIP + "/")) {
                                    questionAnswerOption4Field[pos].setText(bodyText[pos][4].substring(0, 3) + "../" + 
                                            bodyText[pos][4].substring(bodyText[pos][4].lastIndexOf("/") + 1));
                                }
                                onePanel.add(questionAnswerOption4Field[pos]);
                                //
                                bodyText[pos][5] = 
                                        getOneDetail("select answeroptione from questionprofile "
                                        + "where counter = '" + resList[pos] + "'");
                                //
                                printExcelText += bodyText[pos][5] + "\t";
                                //
                                questionAnswerOption5Field[pos] = new JTextField(bodyText[pos][5], 8);
                                questionAnswerOption5Field[pos].setForeground(Color.BLACK);
                                questionAnswerOption5Field[pos].setBorder(BorderFactory.createEmptyBorder());
                                questionAnswerOption5Field[pos].setFont(new Font(getFontType, Font.PLAIN, 12));
                                questionAnswerOption5Field[pos].setEditable(false);
                                questionAnswerOption5Field[pos].setToolTipText(
                                        bodyText[pos][5].toUpperCase());
                                if(bodyText[pos][5].startsWith("//" + getServerIP + "/")) {
                                    questionAnswerOption5Field[pos].setText(bodyText[pos][5].substring(0, 3) + "../" + 
                                            bodyText[pos][5].substring(bodyText[pos][5].lastIndexOf("/") + 1));
                                }
                                //
                                onePanel.add(questionAnswerOption5Field[pos]);
                                //
                                answerCorrectOptionField[pos] = new JTextField(
                                        getOneDetail("select correctoption from questionprofile "
                                        + "where counter = '" + resList[pos] + "'").toUpperCase(), 4);
                                answerCorrectOptionField[pos].setForeground(Color.BLACK);
                                answerCorrectOptionField[pos].setBorder(BorderFactory.createEmptyBorder());
                                answerCorrectOptionField[pos].setFont(new Font(getFontType, Font.PLAIN, 12));
                                answerCorrectOptionField[pos].setEditable(false);
                                onePanel.add(answerCorrectOptionField[pos]);
                                //
                                printExcelText += answerCorrectOptionField[pos].getText() + "\t";
                                //
                                questionBatchField[pos] = new JTextField(
                                        getOneDetail("select questionbatchnumber from questionprofile "
                                        + "where counter = '" + resList[pos] + "'").toUpperCase(), 4);
                                questionBatchField[pos].setForeground(Color.BLACK);
                                questionBatchField[pos].setBorder(BorderFactory.createEmptyBorder());
                                questionBatchField[pos].setFont(new Font(getFontType, Font.PLAIN, 12));
                                questionBatchField[pos].setEditable(false);
                                onePanel.add(questionBatchField[pos]);
                                //
                                printExcelText += questionBatchField[pos].getText() + "\t";
                                //
                                dateField[pos] = new JTextField(
                                        getOneDetail("select datecreated from questionprofile "
                                        + "where counter = '" + resList[pos] + "'").toUpperCase(), 11);
                                dateField[pos].setForeground(Color.BLACK);
                                dateField[pos].setBorder(BorderFactory.createEmptyBorder());
                                dateField[pos].setFont(new Font(getFontType, Font.PLAIN, 12));
                                dateField[pos].setEditable(false);
                                onePanel.add(dateField[pos]);
                                //
                                printExcelText += dateField[pos].getText() + "\n";
                                //
                                try{sleep(80);}catch(InterruptedException ererer){}
                                //
                                questionUpdateButton[pos] = new JButton("Update");
                                questionUpdateButton[pos].setForeground(Color.BLUE);
                                questionUpdateButton[pos].setBackground(themeColor);
                                questionUpdateButton[pos].setFont(new Font(getFontType, Font.BOLD, 10));
                                questionUpdateButton[pos].addActionListener(new saveUpdate(pos));
                                //
                                onePanel.add(new JLabel("  "));
                                onePanel.add(questionUpdateButton[pos]);
                                //
                                if (Math.IEEEremainder(pos, 2) == 0) {
                                    onePanel.setBackground(themeColor);
                                    questionIDField[pos].setBackground(themeColor);
                                    questionBodyArea[pos].setBackground(themeColor);
                                    questionAnswerOption1Field[pos].setBackground(themeColor);
                                    questionAnswerOption2Field[pos].setBackground(themeColor);
                                    questionAnswerOption3Field[pos].setBackground(themeColor);
                                    questionAnswerOption4Field[pos].setBackground(themeColor);
                                    questionAnswerOption5Field[pos].setBackground(themeColor);
                                    answerCorrectOptionField[pos].setBackground(themeColor);
                                    questionBatchField[pos].setBackground(themeColor);
                                    dateField[pos].setBackground(themeColor);
                                    questionCheckBox[pos].setBackground(themeColor);
                                } 
                                else {
                                    onePanel.setBackground(Color.LIGHT_GRAY);
                                    questionIDField[pos].setBackground(Color.LIGHT_GRAY);
                                    questionBodyArea[pos].setBackground(Color.LIGHT_GRAY);
                                    questionAnswerOption1Field[pos].setBackground(Color.LIGHT_GRAY);
                                    questionAnswerOption2Field[pos].setBackground(Color.LIGHT_GRAY);
                                    questionAnswerOption3Field[pos].setBackground(Color.LIGHT_GRAY);
                                    questionAnswerOption4Field[pos].setBackground(Color.LIGHT_GRAY);
                                    questionAnswerOption5Field[pos].setBackground(Color.LIGHT_GRAY);
                                    answerCorrectOptionField[pos].setBackground(Color.LIGHT_GRAY);
                                    questionBatchField[pos].setBackground(Color.LIGHT_GRAY);
                                    dateField[pos].setBackground(Color.LIGHT_GRAY);
                                    questionCheckBox[pos].setBackground(Color.LIGHT_GRAY);
                                }
                                //
                                subjectBoxPanel.add(onePanel);
                            }
                            //
                            commQuestionLab.setText(" Uploaded " + resCount + " Records From "
                                        + "Selected Parameters.");
                        }
                        else if(questionMode.equalsIgnoreCase("theory")) {
                            resList = populateList("select counter from theoryquestionprofile "
                                    + "where questionsession = '" + questionYear + "' "
                                    + "and questionterm = '" + questionTerm + "' "
                                    + "and questionclass = '" + questionClass + "' "
                                    + "and questionsubject = '" + questionSubject + "' "
                                    + "and questionmode = '" + questionMode + "' "
                                    + "and questionbatchnumber = '" + questionBatch + "' "
                                    + "order by questionid", resCount);
                            //
                            JTextField questionIDLab = new JTextField("ID", 3);
                            questionIDLab.setForeground(Color.BLUE);
                            questionIDLab.setFont(new Font(getFontType, Font.BOLD, 14));
                            questionIDLab.setBorder(BorderFactory.createEmptyBorder());
                            questionIDLab.setBackground(Color.LIGHT_GRAY);
                            questionIDLab.setEditable(false);
                            //
                            JTextField questionHeaderLab = new JTextField("QUESTION BODY", 28);
                            questionHeaderLab.setForeground(Color.BLUE);
                            questionHeaderLab.setFont(new Font(getFontType, Font.BOLD, 14));
                            questionHeaderLab.setBorder(BorderFactory.createEmptyBorder());
                            questionHeaderLab.setBackground(Color.LIGHT_GRAY);
                            questionHeaderLab.setEditable(false);
                            //
                            JTextField batchLab = new JTextField("BATCH", 4);
                            batchLab.setForeground(Color.BLUE);
                            batchLab.setFont(new Font(getFontType, Font.BOLD, 14));
                            batchLab.setBorder(BorderFactory.createEmptyBorder());
                            batchLab.setBackground(Color.LIGHT_GRAY);
                            batchLab.setEditable(false);
                            //
                            JTextField dateLab = new JTextField("DATE", 10);
                            dateLab.setForeground(Color.BLUE);
                            dateLab.setFont(new Font(getFontType, Font.BOLD, 14));
                            dateLab.setBorder(BorderFactory.createEmptyBorder());
                            dateLab.setBackground(Color.LIGHT_GRAY);
                            dateLab.setEditable(false);
                            //
                            headerPanel.add(new JLabel("         "));
                            headerPanel.add(questionIDLab);
                            headerPanel.add(questionHeaderLab);
                            headerPanel.add(batchLab);
                            headerPanel.add(dateLab);
                            //
                            printExcelText += questionIDLab.getText() + "\t";
                            printExcelText += questionHeaderLab.getText() + "\t";
                            printExcelText += batchLab.getText() + "\t";
                            printExcelText += dateLab.getText() + "\n";
                            //
                            headerLeftPanel.setLayout(new BorderLayout());
                            headerLeftPanel.add(headerPanel, BorderLayout.WEST);
                            //
                            questionIDField = new JTextField[resCount];
                            questionBatchField = new JTextField[resCount];
                            JTextField[] dateField = new JTextField[resCount];
                            bodyText = new String[resCount][6];
                            //
                            questionBodyArea = new JTextArea[resCount];
                            //
                            questionCheckBox = new JCheckBox[resCount];
                            questionUpdateButton = new JButton[resCount];
                            //
                            for (int pos = 0; pos < resCount; pos++) {
                                JPanel onePanel = new JPanel();
                                //
                                commQuestionLab.setText(" Uploading " + (pos + 1) + " Records From "
                                        + "Selected Parameters ...Please Wait!");
                                //
                                questionCheckBox[pos] = new JCheckBox();
                                questionCheckBox[pos].setBackground(themeColor);
                                questionCheckBox[pos].addItemListener(new enableField(pos));
                                onePanel.add(questionCheckBox[pos]);
                                //
                                questionIDField[pos] = new JTextField(
                                        getOneDetail("select questionid from theoryquestionprofile "
                                        + "where counter = '" + resList[pos] + "'"), 3);
                                questionIDField[pos].setForeground(Color.BLACK);
                                questionIDField[pos].setBorder(BorderFactory.createEmptyBorder());
                                questionIDField[pos].setFont(new Font(getFontType, Font.PLAIN, 12));
                                questionIDField[pos].setEditable(false);
                                onePanel.add(questionIDField[pos]);
                                //
                                printExcelText += questionIDField[pos].getText() + "\t";
                                //
                                bodyText[pos][0] = 
                                        getOneDetail("select questionbody from theoryquestionprofile "
                                        + "where counter = '" + resList[pos] + "'");
                                questionBodyArea[pos] = new JTextArea(bodyText[pos][0], 5, 30);
                                questionBodyArea[pos].setForeground(Color.BLACK);
                                questionBodyArea[pos].setLineWrap(true);
                                questionBodyArea[pos].setWrapStyleWord(true);
                                questionBodyArea[pos].setFont(new Font(getFontType, Font.PLAIN, 12));
                                questionBodyArea[pos].setEditable(false);
                                onePanel.add(new JScrollPane(questionBodyArea[pos]));
                                //
                                printExcelText += questionBodyArea[pos].getText() + "\t";
                                //
                                questionBatchField[pos] = new JTextField(
                                        getOneDetail("select questionbatchnumber from theoryquestionprofile "
                                        + "where counter = '" + resList[pos] + "'").toUpperCase(), 4);
                                questionBatchField[pos].setForeground(Color.BLACK);
                                questionBatchField[pos].setBorder(BorderFactory.createEmptyBorder());
                                questionBatchField[pos].setFont(new Font(getFontType, Font.PLAIN, 12));
                                questionBatchField[pos].setEditable(false);
                                onePanel.add(questionBatchField[pos]);
                                //
                                printExcelText += questionBatchField[pos].getText() + "\t";
                                //
                                dateField[pos] = new JTextField(
                                        getOneDetail("select datecreated from theoryquestionprofile "
                                        + "where counter = '" + resList[pos] + "'").toUpperCase(), 11);
                                dateField[pos].setForeground(Color.BLACK);
                                dateField[pos].setBorder(BorderFactory.createEmptyBorder());
                                dateField[pos].setFont(new Font(getFontType, Font.PLAIN, 12));
                                dateField[pos].setEditable(false);
                                onePanel.add(dateField[pos]);
                                //
                                printExcelText += dateField[pos].getText() + "\n";
                                //
                                try{sleep(80);}catch(InterruptedException ererer){}
                                //
                                questionUpdateButton[pos] = new JButton("Update");
                                questionUpdateButton[pos].setForeground(Color.BLUE);
                                questionUpdateButton[pos].setBackground(themeColor);
                                questionUpdateButton[pos].setFont(new Font(getFontType, Font.BOLD, 10));
                                questionUpdateButton[pos].addActionListener(new saveUpdate(pos));
                                //
                                onePanel.add(new JLabel("  "));
                                onePanel.add(questionUpdateButton[pos]);
                                //
                                if (Math.IEEEremainder(pos, 2) == 0) {
                                    onePanel.setBackground(themeColor);
                                    questionIDField[pos].setBackground(themeColor);
                                    questionBodyArea[pos].setBackground(themeColor);
                                    questionBatchField[pos].setBackground(themeColor);
                                    dateField[pos].setBackground(themeColor);
                                    questionCheckBox[pos].setBackground(themeColor);
                                } 
                                else {
                                    onePanel.setBackground(Color.LIGHT_GRAY);
                                    questionIDField[pos].setBackground(Color.LIGHT_GRAY);
                                    questionBodyArea[pos].setBackground(Color.LIGHT_GRAY);
                                    questionBatchField[pos].setBackground(Color.LIGHT_GRAY);
                                    dateField[pos].setBackground(Color.LIGHT_GRAY);
                                    questionCheckBox[pos].setBackground(Color.LIGHT_GRAY);
                                }
                                //
                                subjectBoxPanel.add(onePanel);
                            }
                            //
                            commQuestionLab.setText(" Uploaded " + resCount + " Records From "
                                        + "Selected Parameters.");
                        }
                    } 
                    else {
                        subjectBoxPanel.removeAll();
                        subjectBoxPanel.add(new JLabel("No Question Details Yet!"));
                    }
                    //
                    JPanel cenSubjectBoxPanel = new JPanel();
                    cenSubjectBoxPanel.setBackground(themeColor);
                    cenSubjectBoxPanel.add(subjectBoxPanel);
                    //
                    JPanel cenPanel = new JPanel();
                    cenPanel.setBackground(themeColor);
                    cenPanel.setLayout(new BorderLayout());
                    cenPanel.add(headerLeftPanel, BorderLayout.NORTH);
                    cenPanel.add(new JScrollPane(cenSubjectBoxPanel), BorderLayout.CENTER);
                    //
                    questionDisplayPanel.removeAll();
                    questionDisplayPanel.add(cenPanel, BorderLayout.WEST);
                    validate();
                }
            }
            //
            class displayImage extends MouseAdapter {
                int pos = 0;
                String link = "",
                        id = "",
                        optionLetter = "";
                //
                JPanel displayPanel = new JPanel();
                //
                public displayImage(int p, String l, 
                        String i, String let) {
                    pos = p; 
                    link = l;
                    id = i;
                    optionLetter = let;
                }
                public void mouseClicked(MouseEvent e) {
                    //
                    try {
                        promptLab.setText("Select Input Format.");
                        String[] optList = {"Text Format", "Image Format"};
                        //
                        String getOpt = (String)JOptionPane.showInputDialog(null, 
                                promptLab,
                                getSchoolName.toUpperCase(),
                                JOptionPane.INFORMATION_MESSAGE,
                                infoIcon,
                                optList,
                                optList[0]);
                        //
                        if(getOpt.equalsIgnoreCase("image format")) {
                            //
                            displayPanel.removeAll();
                            displayPanel.setBackground(Color.WHITE);
                            displayPanel.setLayout(new BorderLayout());
                            displayPanel.add(new JScrollPane(new JLabel(
                                    new ImageIcon(link))), BorderLayout.CENTER);
                            //
                            promptLab.setText("Do you Want To Change QUESTION OR "
                                    + "ANSWER OPTION Picture File?");
                            displayPanel.add(promptLab, BorderLayout.SOUTH);
                            validate();
                            //
                            int reply = JOptionPane.showConfirmDialog(null, 
                                    displayPanel,
                                    "ID: " + id + (!optionLetter.equalsIgnoreCase("") ? 
                                                " - ANSWER OPTION " + 
                                    optionLetter : " - QUESTION"),
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.PLAIN_MESSAGE);
                            //
                            if(reply == JOptionPane.YES_OPTION) {
                                previewImage(optionLetter, pos);
                            }
                        }
                        //
                        if(getOpt.equalsIgnoreCase("text format")) {
                            promptLab.setText("Please Type Update Text On The Field");
                            JOptionPane.showMessageDialog(null, 
                                    promptLab,
                                    getSchoolName.toUpperCase(),
                                    JOptionPane.INFORMATION_MESSAGE,
                                    infoIcon);
                        }
                        return;
                    }catch(Exception erer){}
                }
                //
                void previewImage(String key, int pos) {
                    //load staff logo
                    JFileChooser ch = new JFileChooser();
                    ch.setDialogTitle("Image Preview");
                    ch.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    int rep = ch.showOpenDialog(null);
                    if (rep == JFileChooser.CANCEL_OPTION) {
                        promptLab.setText(" Preview Operation Terminated.");
                        JOptionPane.showMessageDialog(null,
                                promptLab,
                                getSchoolName.toUpperCase(),
                                JOptionPane.INFORMATION_MESSAGE,
                                infoIcon);
                    } 
                    else {
                        File dataFile = null;
                        //get file and check for no file selected
                        dataFile = ch.getSelectedFile();
                        if (dataFile.equals(null)
                                || dataFile.getName().equalsIgnoreCase("")) {
                            promptLab.setText(" File Name Not Saved.");
                            JOptionPane.showMessageDialog(null,
                                    promptLab,
                                    getSchoolName.toUpperCase(),
                                    JOptionPane.INFORMATION_MESSAGE,
                                    infoIcon);
                        } 
                        else {
                            String format = "";
                            //change the back slash to front slash
                            for (int x = 0; x < dataFile.getAbsolutePath().length(); x++) {
                                if (dataFile.getAbsolutePath().charAt(x) == '\\') {
                                    format = dataFile.getAbsolutePath().replaceAll("\\\\", "/");
                                }
                            }
                            //
                            format = format.replaceAll("\\\\", "\\");
                            //
                            displayPanel.removeAll();
                            displayPanel.setLayout(new BorderLayout());
                            displayPanel.add(new JScrollPane(new JLabel(
                                    new ImageIcon(format))), BorderLayout.CENTER);
                            //
                            promptLab.setText("Continue?");
                            displayPanel.add(promptLab, BorderLayout.SOUTH);
                            validate();
                            //
                            int reply = JOptionPane.showConfirmDialog(null, 
                                    displayPanel,
                                    "ID: " + id + (!optionLetter.equalsIgnoreCase("") ? 
                                                " OPTION " + optionLetter : ""),
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.PLAIN_MESSAGE);
                            //
                            if(reply == JOptionPane.YES_OPTION) {
                                if(key.equalsIgnoreCase("a")) {
                                    questionAnswerOption1Field[pos].setText(format);
                                }
                                else if(key.equalsIgnoreCase("b")) {
                                    questionAnswerOption2Field[pos].setText(format);
                                }
                                else if(key.equalsIgnoreCase("c")) {
                                    questionAnswerOption3Field[pos].setText(format);
                                }
                                else if(key.equalsIgnoreCase("d")) {
                                    questionAnswerOption4Field[pos].setText(format);
                                }
                                else if(key.equalsIgnoreCase("e")) {
                                    questionAnswerOption5Field[pos].setText(format);
                                }
                                else {
                                    questionBodyArea[pos].setText(format);
                                }
                                //
                                questionAnswerOption1Field[pos].setEditable(false);
                                questionAnswerOption2Field[pos].setEditable(false);
                                questionAnswerOption3Field[pos].setEditable(false);
                                questionAnswerOption4Field[pos].setEditable(false);
                                questionAnswerOption5Field[pos].setEditable(false);
                                answerCorrectOptionField[pos].setEditable(false);
                                questionBodyArea[pos].setEditable(false);
                            }
                        }
                    }
                }
            }
            //
            class enableField implements ItemListener {
                int pos = 0;
                public enableField(int p) {
                    pos = p;
                }
                public void itemStateChanged(ItemEvent e) {
                    if(getQuestionMode.equalsIgnoreCase("theory")) {
                        if(questionCheckBox[pos].isSelected()) {
                            questionBodyArea[pos].setBorder(BorderFactory.createEtchedBorder());
                            questionBodyArea[pos].setEditable(true);
                            questionUpdateButton[pos].setForeground(Color.BLUE);
                            //
                            questionBodyArea[pos].setCursor(
                                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                            questionBodyArea[pos].addMouseListener(
                                new displayImage(pos, bodyText[pos][0], 
                                questionIDField[pos].getText(), ""));
                            //
                            questionBodyArea[pos].setBackground(Color.WHITE);
                        } 
                        else {
                            //
                            if (questionCheckBox[pos].getBackground() == Color.LIGHT_GRAY) {
                                questionIDField[pos].setBackground(Color.LIGHT_GRAY);
                                questionBodyArea[pos].setBackground(Color.LIGHT_GRAY);
                            } 
                            else {
                                questionIDField[pos].setBackground(themeColor);
                                questionBodyArea[pos].setBackground(themeColor);
                            }
                            //
                            questionBodyArea[pos].setBorder(BorderFactory.createEmptyBorder());
                            questionBodyArea[pos].setEditable(false);
                            questionUpdateButton[pos].setForeground(Color.RED);
                        }
                    }
                    else if(getQuestionMode.equalsIgnoreCase("objective")) {
                        if (questionCheckBox[pos].isSelected()) {
                            questionAnswerOption1Field[pos].setBorder(BorderFactory.createEtchedBorder());
                            questionAnswerOption1Field[pos].setEditable(true);
                            questionAnswerOption2Field[pos].setBorder(BorderFactory.createEtchedBorder());
                            questionAnswerOption2Field[pos].setEditable(true);
                            questionAnswerOption3Field[pos].setBorder(BorderFactory.createEtchedBorder());
                            questionAnswerOption3Field[pos].setEditable(true);
                            questionAnswerOption4Field[pos].setBorder(BorderFactory.createEtchedBorder());
                            questionAnswerOption4Field[pos].setEditable(true);
                            questionAnswerOption5Field[pos].setBorder(BorderFactory.createEtchedBorder());
                            questionAnswerOption5Field[pos].setEditable(true);
                            answerCorrectOptionField[pos].setBorder(BorderFactory.createEtchedBorder());
                            answerCorrectOptionField[pos].setEditable(true);
                            questionBodyArea[pos].setBorder(BorderFactory.createEtchedBorder());
                            questionBodyArea[pos].setEditable(true);
                            questionUpdateButton[pos].setForeground(Color.BLUE);
                            //
                            questionBodyArea[pos].setCursor(
                                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                            questionBodyArea[pos].addMouseListener(
                                new displayImage(pos, bodyText[pos][0], 
                                questionIDField[pos].getText(), ""));
                            //
                            questionAnswerOption1Field[pos].setCursor(
                                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                            questionAnswerOption1Field[pos].addMouseListener(
                                new displayImage(pos, bodyText[pos][1], 
                                questionIDField[pos].getText(), "A"));
                            //
                            questionAnswerOption2Field[pos].setCursor(
                                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                            questionAnswerOption2Field[pos].addMouseListener(
                                new displayImage(pos, bodyText[pos][2], 
                                questionIDField[pos].getText(), "B"));
                            //
                            questionAnswerOption3Field[pos].setCursor(
                                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                            questionAnswerOption3Field[pos].addMouseListener(
                                new displayImage(pos, bodyText[pos][3], 
                                questionIDField[pos].getText(), "C"));
                            //
                            questionAnswerOption4Field[pos].setCursor(
                                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                            questionAnswerOption4Field[pos].addMouseListener(
                                new displayImage(pos, bodyText[pos][4], 
                                questionIDField[pos].getText(), "D"));
                            //
                            questionAnswerOption5Field[pos].setCursor(
                                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                            questionAnswerOption5Field[pos].addMouseListener(
                                new displayImage(pos, bodyText[pos][5], 
                                questionIDField[pos].getText(), "E"));
                            //
                            questionAnswerOption1Field[pos].setBackground(Color.WHITE);
                            questionAnswerOption2Field[pos].setBackground(Color.WHITE);
                            questionAnswerOption3Field[pos].setBackground(Color.WHITE);
                            questionAnswerOption4Field[pos].setBackground(Color.WHITE);
                            questionAnswerOption5Field[pos].setBackground(Color.WHITE);
                            answerCorrectOptionField[pos].setBackground(Color.WHITE);
                            questionBodyArea[pos].setBackground(Color.WHITE);
                        } 
                        else {
                            //
                            if (questionCheckBox[pos].getBackground() == Color.LIGHT_GRAY) {
                                questionIDField[pos].setBackground(Color.LIGHT_GRAY);
                                questionAnswerOption1Field[pos].setBackground(Color.LIGHT_GRAY);
                                questionAnswerOption2Field[pos].setBackground(Color.LIGHT_GRAY);
                                questionAnswerOption3Field[pos].setBackground(Color.LIGHT_GRAY);
                                questionAnswerOption4Field[pos].setBackground(Color.LIGHT_GRAY);
                                questionAnswerOption5Field[pos].setBackground(Color.LIGHT_GRAY);
                                answerCorrectOptionField[pos].setBackground(Color.LIGHT_GRAY);
                                questionBodyArea[pos].setBackground(Color.LIGHT_GRAY);
                            } 
                            else {
                                questionIDField[pos].setBackground(themeColor);
                                questionAnswerOption1Field[pos].setBackground(themeColor);
                                questionAnswerOption2Field[pos].setBackground(themeColor);
                                questionAnswerOption3Field[pos].setBackground(themeColor);
                                questionAnswerOption4Field[pos].setBackground(themeColor);
                                questionAnswerOption5Field[pos].setBackground(themeColor);
                                answerCorrectOptionField[pos].setBackground(themeColor);
                                questionBodyArea[pos].setBackground(themeColor);
                            }
                            //
                            questionAnswerOption1Field[pos].setBorder(BorderFactory.createEmptyBorder());
                            questionAnswerOption1Field[pos].setEditable(false);
                            questionAnswerOption2Field[pos].setBorder(BorderFactory.createEmptyBorder());
                            questionAnswerOption2Field[pos].setEditable(false);
                            questionAnswerOption3Field[pos].setBorder(BorderFactory.createEmptyBorder());
                            questionAnswerOption3Field[pos].setEditable(false);
                            questionAnswerOption4Field[pos].setBorder(BorderFactory.createEmptyBorder());
                            questionAnswerOption4Field[pos].setEditable(false);
                            questionAnswerOption5Field[pos].setBorder(BorderFactory.createEmptyBorder());
                            questionAnswerOption5Field[pos].setEditable(false);
                            answerCorrectOptionField[pos].setBorder(BorderFactory.createEmptyBorder());
                            answerCorrectOptionField[pos].setEditable(false);
                            questionBodyArea[pos].setBorder(BorderFactory.createEmptyBorder());
                            questionBodyArea[pos].setEditable(false);
                            questionUpdateButton[pos].setForeground(Color.RED);
                        }
                    }
                }
            }
            //
            class saveUpdate implements ActionListener {
                int pos = 0;
                public saveUpdate(int p) {
                    pos = p;
                }
                public void actionPerformed(ActionEvent e) {
                    if(getQuestionMode.equalsIgnoreCase("theory")) {
                        if (!questionBodyArea[pos].getText().equalsIgnoreCase("")
                                && !getQuestionClass.equalsIgnoreCase("")
                                && !getQuestionTerm.equalsIgnoreCase("")
                                && !getQuestionMode.equalsIgnoreCase("")
                                && !getQuestionSubject.equalsIgnoreCase("")
                                && !getQuestionSession.equalsIgnoreCase("")) {
                            //
                            questionBodyArea[pos].setBorder(BorderFactory.createEmptyBorder());
                            questionBodyArea[pos].setEditable(false);
                            questionUpdateButton[pos].setForeground(Color.BLUE);
                            //
                            if (questionCheckBox[pos].isSelected()) {
                                questionCheckBox[pos].setSelected(false);
                            }
                            //
                            insertUpdateDelete("update theoryquestionprofile "
                                + "set questionbody = '" + questionBodyArea[pos].getText() + "' "
                                    + "where questionclass = '" + getQuestionClass + "' "
                                    + "and questionterm = '" + getQuestionTerm + "' "
                                    + "and questionsession = '" + getQuestionSession + "' "
                                    + "and questionsubject = '" + getQuestionSubject + "' "
                                    + "and questionid = '" + questionIDField[pos].getText() + "' "
                                    + "and questionmode = '" + getQuestionMode + "' "
                                    + "and questionbatchnumber = '" + 
                                                        questionBatchField[pos].getText() + "'");
                            //
                            if (questionCheckBox[pos].getBackground() == Color.LIGHT_GRAY) {
                                questionIDField[pos].setBackground(Color.LIGHT_GRAY);
                                questionBodyArea[pos].setBackground(Color.LIGHT_GRAY);
                            }
                        } 
                        else {
                            promptLab.setText("Please Enter Data In Empty Field OR "
                                    + "Test Class Not Selected.");
                            JOptionPane.showMessageDialog(null,
                                    promptLab,
                                    getSchoolName.toUpperCase(),
                                    JOptionPane.INFORMATION_MESSAGE,
                                    infoIcon);
                        }
                    }
                    else if(getQuestionMode.equalsIgnoreCase("objective")) {
                        if (!questionAnswerOption1Field[pos].getText().equalsIgnoreCase("")
                                && !questionAnswerOption2Field[pos].getText().equalsIgnoreCase("")
                                && !questionAnswerOption3Field[pos].getText().equalsIgnoreCase("")
                                && !questionAnswerOption4Field[pos].getText().equalsIgnoreCase("")
                                && !questionAnswerOption5Field[pos].getText().equalsIgnoreCase("")
                                && !questionBodyArea[pos].getText().equalsIgnoreCase("")
                                && !answerCorrectOptionField[pos].getText().equalsIgnoreCase("")
                                && !getQuestionClass.equalsIgnoreCase("")
                                && !getQuestionMode.equalsIgnoreCase("")
                                && !getQuestionTerm.equalsIgnoreCase("")
                                && !getQuestionSubject.equalsIgnoreCase("")
                                && !getQuestionSession.equalsIgnoreCase("")) {
                            //
                            questionAnswerOption1Field[pos].setBorder(BorderFactory.createEmptyBorder());
                            questionAnswerOption1Field[pos].setEditable(false);
                            questionAnswerOption2Field[pos].setBorder(BorderFactory.createEmptyBorder());
                            questionAnswerOption2Field[pos].setEditable(false);
                            questionAnswerOption3Field[pos].setBorder(BorderFactory.createEmptyBorder());
                            questionAnswerOption3Field[pos].setEditable(false);
                            questionAnswerOption4Field[pos].setBorder(BorderFactory.createEmptyBorder());
                            questionAnswerOption4Field[pos].setEditable(false);
                            questionAnswerOption5Field[pos].setBorder(BorderFactory.createEmptyBorder());
                            questionAnswerOption5Field[pos].setEditable(false);
                            answerCorrectOptionField[pos].setBorder(BorderFactory.createEmptyBorder());
                            answerCorrectOptionField[pos].setEditable(false);
                            questionBodyArea[pos].setBorder(BorderFactory.createEmptyBorder());
                            questionBodyArea[pos].setEditable(false);
                            questionUpdateButton[pos].setForeground(Color.RED);
                            //
                            if (questionCheckBox[pos].isSelected()) {
                                questionCheckBox[pos].setSelected(false);
                            }
                            //
                            insertUpdateDelete("update questionprofile "
                                + "set questionbody = '" + questionBodyArea[pos].getText() + "', "
                                    + "answeroptiona = '" + questionAnswerOption1Field[pos].getText() + "', "
                                    + "answeroptionb = '" + questionAnswerOption2Field[pos].getText() + "', "
                                    + "answeroptionc = '" + questionAnswerOption3Field[pos].getText() + "', "
                                    + "answeroptiond = '" + questionAnswerOption4Field[pos].getText() + "', "
                                    + "answeroptione = '" + questionAnswerOption5Field[pos].getText() + "', "
                                    + "correctoption = '" + answerCorrectOptionField[pos].getText() + "' "
                                    + "where questionclass = '" + getQuestionClass + "' "
                                    + "and questionterm = '" + getQuestionTerm + "' "
                                    + "and questionsession = '" + getQuestionSession + "' "
                                    + "and questionsubject = '" + getQuestionSubject + "' "
                                    + "and questionid = '" + questionIDField[pos].getText() + "' "
                                    + "and questionmode = '" + getQuestionMode + "' "
                                    + "and questionbatchnumber = '" + 
                                                        questionBatchField[pos].getText() + "'");
                            //
                            if (questionCheckBox[pos].getBackground() == Color.LIGHT_GRAY) {
                                questionIDField[pos].setBackground(Color.LIGHT_GRAY);
                                questionAnswerOption1Field[pos].setBackground(Color.LIGHT_GRAY);
                                questionAnswerOption2Field[pos].setBackground(Color.LIGHT_GRAY);
                                questionAnswerOption3Field[pos].setBackground(Color.LIGHT_GRAY);
                                questionAnswerOption4Field[pos].setBackground(Color.LIGHT_GRAY);
                                questionAnswerOption5Field[pos].setBackground(Color.LIGHT_GRAY);
                                answerCorrectOptionField[pos].setBackground(Color.LIGHT_GRAY);
                                questionBodyArea[pos].setBackground(Color.LIGHT_GRAY);
                            }
                        } 
                        else {
                            promptLab.setText("Please Enter Data In Empty Field OR "
                                    + "Test Class Not Selected.");
                            JOptionPane.showMessageDialog(null,
                                    promptLab,
                                    getSchoolName.toUpperCase(),
                                    JOptionPane.INFORMATION_MESSAGE,
                                    infoIcon);
                        }
                    }
                    //
                    validate();
                }
            }
        }
        //
        class welcomePanel extends JPanel {

            JPanel northPanel = new JPanel(),
                    centerPanel = new JPanel(),
                    southPanel = new JPanel(),
                    //
                    centerLogoPanel = new JPanel(),
                    schoolNameAddressPanel = new JPanel();
            //
            JLabel welcomeLogoLab = null,
                    schoolNameLab = new JLabel(getSchoolName.toUpperCase(), JLabel.CENTER),
                    schoolAddressLab = new JLabel(getSchoolAddress.toUpperCase(), JLabel.CENTER);
            //
            JPanel[] photoListPanel = null;
            JLabel[][] photoListLab = null;
            //  
            String getLogo = "images/BabyDance.gif";
            //
            public welcomePanel() {
                setBackground(themeColor);
                //
                welcomeLogoLab = new JLabel(new ImageIcon(getLogo), JLabel.CENTER);
                schoolNameLab.setText(getSchoolName.toUpperCase());
                //
                schoolNameLab.setForeground(Color.ORANGE);
                schoolNameLab.setFont(new Font(getFontType, Font.BOLD, 30));
                schoolAddressLab.setForeground(Color.ORANGE);
                schoolAddressLab.setFont(new Font(getFontType, Font.BOLD, 30));
                //
                centerLogoPanel.setLayout(new BorderLayout());
                centerLogoPanel.add(welcomeLogoLab, BorderLayout.CENTER);
                centerLogoPanel.add(schoolNameLab, BorderLayout.SOUTH);
                //
                centerPanel.setLayout(new BorderLayout());
                centerPanel.add(centerLogoPanel, BorderLayout.CENTER);
                //
                centerLogoPanel.setBackground(themeColor);
                schoolNameAddressPanel.setBackground(themeColor);
                //
                centerPanel.setBackground(themeColor);
                //
                setLayout(new BorderLayout());
                add(centerPanel, BorderLayout.CENTER);
                validate();
            }
        }
        //
        void sendToExcel(String getClass, String getTerm, 
                String getSession, String getSubject, 
                String getMode) {
            //
            JFileChooser ch = new JFileChooser();
            ch.setFileSelectionMode(ch.FILES_ONLY);
            ch.setDialogTitle(getSchoolName.toUpperCase());
            ch.setMultiSelectionEnabled(true);
            //display the file dialog box
            int reply = ch.showSaveDialog(null);
            if (reply == ch.CANCEL_OPTION) {
               return;
            }
            File dataFile = ch.getSelectedFile();
            //
            if (dataFile.equals(null) ||
                   dataFile.getName().equalsIgnoreCase("")) {
                //
                promptLab.setText("Selected File Does Not Exist OR "
                        + "Not Selected");
                JOptionPane.showMessageDialog(null,
                        promptLab,
                        getSchoolName.toUpperCase(),
                        JOptionPane.INFORMATION_MESSAGE,
                        infoIcon);
            } 
            else {
                String format = "";
               //change the back slash to front slash
               for(int x = 0; x < dataFile.getAbsolutePath().length(); x++) {
                   if (dataFile.getAbsolutePath().charAt(x) == '\\') {
                       format = dataFile.getAbsolutePath().replaceAll("\\\\", "/");
                   }
               }
               //
               try{
                    PrintWriter out = new PrintWriter(new OutputStreamWriter(
                        new FileOutputStream(format + "_" + getMode + ".xls", true)));
                   //
                   out.println(getSchoolName.toUpperCase());
                   out.flush();
                   //
                   out.println(getSchoolAddress.toUpperCase());
                   out.flush();
                   //
                   out.println("CLASS: " + getClass.toUpperCase() + "  "
                            + "TERM: " + getTerm.toUpperCase() + "  "
                            + "SESSION/YEAR: " + getSession + "  "
                            + "SUBJECT: " + getSubject.toUpperCase() + "  "
                            + "QUESTION MODE: " + getMode.toUpperCase());
                   out.flush();
                   out.println("*************************************************");
                   out.flush();
                   out.println(printExcelText);
                   out.flush();
                   //
                   promptLab.setText("Exported File @ " + format + "_" + getMode + ".xls");
                   JOptionPane.showMessageDialog(null, 
                          promptLab,
                          getSchoolName.toUpperCase(),
                          JOptionPane.INFORMATION_MESSAGE,
                          infoIcon);
                }catch(IOException eio){}
            }
        }
    }
    //
    String[] populateList(String query, int c) {
        String[] list = new String[c];
        int res = 0;
        Connection conn;
        Statement statement;
        ResultSet set;
        try {
            conn = createConnection();
            statement = conn.createStatement();
            set = statement.executeQuery(query);
            //
            if (!set.next()) {
                return null;
            } else {
                ResultSetMetaData data = set.getMetaData();
                Vector row = new Vector(),
                        column = new Vector();
                //
                for (int x = 1; x <= data.getColumnCount(); x++) {
                    column.addElement(data.getColumnName(x));
                }
                do {
                    row.addElement(getNextRow(set, data));
                    list[res] = row.get(res).toString().substring(1,
                            row.get(res).toString().length() - 1);
                    //
                    res++;
                } while (set.next());
                //
                validate();
            }
        } catch (SQLException sql) {
        } catch (Exception gen) {
        }
        //
        return list;
    }
    //
    public String setAmPm() {
        Date date = new Date();
        hour = date.toString().substring(11, 13); //generate instant timer
        String ampmHour = Integer.parseInt(hour) <= 11 ? "AM" : "PM";
        //
        return ampmHour;
    }
    //converting to numeric month
    public String convertToNumericMonth(String mon) {
        //convert string month value to numeric month value
        String monthValue = "";
        if (mon.equalsIgnoreCase("jan")) {
            monthValue = "01";
        }
        if (mon.equalsIgnoreCase("feb")) {
            monthValue = "02";
        }
        if (mon.equalsIgnoreCase("mar")) {
            monthValue = "03";
        }
        if (mon.equalsIgnoreCase("apr")) {
            monthValue = "04";
        }
        if (mon.equalsIgnoreCase("may")) {
            monthValue = "05";
        }
        if (mon.equalsIgnoreCase("jun")) {
            monthValue = "06";
        }
        if (mon.equalsIgnoreCase("jul")) {
            monthValue = "07";
        }
        if (mon.equalsIgnoreCase("aug")) {
            monthValue = "08";
        }
        if (mon.equalsIgnoreCase("sep")) {
            monthValue = "09";
        }
        if (mon.equalsIgnoreCase("oct")) {
            monthValue = "10";
        }
        if (mon.equalsIgnoreCase("nov")) {
            monthValue = "11";
        }
        if (mon.equalsIgnoreCase("dec")) {
            monthValue = "12";
        }
        return monthValue;
    }
    //time/date display class
    class timeDisplay extends Thread {//setting the real time timer class

        Date d;
        int hr, min, sec;
        boolean flag = true;

        public timeDisplay(String hr, String min, String sec, Date d) {
            this.min = Integer.parseInt(min);
            this.hr = setHour(Integer.parseInt(hr));
            this.sec = Integer.parseInt(sec);
            this.d = d;
        }

        public int setHour(int hr) {
            int hour = 0;
            if (hr == 00) {
                hour = 12;
            } else if (hr > 12) {
                hour = hr - 12;
            } else if (hr <= 12) {
                hour = hr;
            }
            //
            return hour;
        }

        public void run() {
            while (true) {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                }
                if (flag) {
                    timeDisposition = setAmPm();
                    //
                    dateTimeLab.setText("" + Integer.toString(hr) + " : "
                            + "" + Integer.toString(min) + " : " + Integer.toString(sec++) + ""
                            + " " + timeDisposition + "\n"
                            + "  " + d.toString().substring(0, 3) + ", "
                            + "" + d.toString().substring(4, 10) + " "
                            + "" + d.toString().substring(24, 28) + "   ");
                    //
                    realHour = Integer.toString(hr);
                    realMinute = Integer.toString(min);
                    timeDispose = timeDisposition;
                    //
                    chkMin(sec);
                    //
                    dateCollector = (day + "-" + mon + "-" + year + " "
                            + "" + ((hr < 10) ? ("0" + hr) : hr) + ":"
                            + "" + ((min < 10) ? ("0" + min) : min) + ":"
                            + "" + ((sec < 10) ? ("0" + sec) : sec) + " "
                            + "" + timeDisposition);
                    //
                    String dd = ((hr < 10) ? ("0" + hr) : hr) + ":"
                            + ((min < 10) ? ("0" + min) : min) + ":"
                            + ((sec < 10) ? ("0" + sec) : sec);
                    //
                    validate();
                }
            }
        }
        //

        public void chkMin(int s) {
            if (s > 59) {
                sec = 0;
                dateTimeLab.setText("" + Integer.toString(hr) + " : "
                        + "" + Integer.toString(min++) + " : " + Integer.toString(sec) + ""
                        + " " + timeDisposition + "\n"
                        + "  " + d.toString().substring(0, 3) + ", "
                        + "" + d.toString().substring(4, 10) + " "
                        + "" + d.toString().substring(24, 28) + "   ");
                chkHr(min);
            }
        }
        //

        public void chkHr(int m) {
            if (m > 59) {
                min = 0;
                dateTimeLab.setText("" + Integer.toString(hr++) + " : "
                        + "" + Integer.toString(min) + " : " + Integer.toString(sec) + " "
                        + "" + timeDisposition + "\n"
                        + "  " + d.toString().substring(0, 3) + ", "
                        + "" + d.toString().substring(4, 10) + " "
                        + "" + d.toString().substring(24, 28) + "   ");
                setHr(hr);
            }
        }
        //

        public void setHr(int h) {
            if (h > 12) {
                hr = 1;
                //    
                dateTimeLab.setText("" + Integer.toString(hr) + " : "
                        + "" + Integer.toString(min) + " : " + Integer.toString(sec) + ""
                        + " " + timeDisposition + "\n"
                        + "  " + d.toString().substring(0, 3) + ", "
                        + "" + d.toString().substring(4, 10) + " "
                        + "" + d.toString().substring(24, 28) + "   ");
            }
        }
    }
    //
    class userActivityDialog extends JFrame {

        JLabel headerLab = new JLabel("ACTIVITIES PERFORMED", JLabel.LEFT);
        //
        JTextArea[] contentArea = null;
        //
        JPanel contentPanel = new JPanel(),
                northContentPanel = new JPanel(),
                centraliseContentPanel = new JPanel();
        //
        String[] monthList = {"january", "february", "march",
            "april", "may", "june", "july", "august",
            "september", "october", "november", "december"},
                yearList = {"2019",
            "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027",
            "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035",
            "2036", "2037", "2038", "2039", "2040", "2041", "2042", "2043",
            "2044", "2045", "2046", "2047", "2048", "2049", "2050"};
        JComboBox monthBox = new JComboBox(monthList);
        JComboBox yearBox = new JComboBox(yearList);
        //search
        JLabel dateLab = new JLabel("Select Date:");
        //
        String getMonth = "", getYear = "";
        String[] userLogList = null;

        public userActivityDialog() {
            //
            headerLab.setFont(new Font(getFontType, Font.BOLD, 14));
            headerLab.setForeground(Color.BLUE);
            dateLab.setFont(new Font(getFontType, Font.PLAIN, 14));
            dateLab.setForeground(Color.BLUE);
            monthBox.setFont(new Font(getFontType, Font.PLAIN, 12));
            yearBox.setFont(new Font(getFontType, Font.PLAIN, 12));
            monthBox.setForeground(Color.BLACK);
            yearBox.setForeground(Color.BLACK);
            //
            JPanel datePanel = new JPanel();
            datePanel.setBackground(themeColor);
            datePanel.add(dateLab);
            datePanel.add(new JLabel("  "));
            datePanel.add(monthBox);
            datePanel.add(yearBox);
            //
            northContentPanel.setLayout(new BorderLayout());
            northContentPanel.add(datePanel, BorderLayout.EAST);
            northContentPanel.add(headerLab, BorderLayout.WEST);
            northContentPanel.setBackground(themeColor);
            //
            createDisplay(letterMonth(Integer.parseInt(mon)), "20" + year);
            //
            add(northContentPanel, BorderLayout.NORTH);
            add(new JScrollPane(centraliseContentPanel), BorderLayout.CENTER);
            //
            monthBox.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    for (int x = 0; x < monthList.length; x++) {
                        if (x == monthBox.getSelectedIndex()) {
                            getMonth = monthList[x];
                        }
                    }
                }
            });
            //
            yearBox.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    for (int x = 0; x < yearList.length; x++) {
                        if (x == yearBox.getSelectedIndex()) {
                            getYear = yearList[x];
                        }
                    }
                    //
                    createDisplay(getMonth, getYear);
                }
            });
        }
        //
        void createDisplay(String month, String year) {
            //
            int count = getNumberOfDetails("select * from useractivitymonitor "
                    + "where activitydate like '%" + (month + " " + year) + "%'");
            //
            if (count == 0) {
                promptLab.setText(" No User Activity Report For Period Specified.");
            } else {
                //
                centraliseContentPanel.removeAll();
                centraliseContentPanel.setBackground(Color.WHITE);
                //
                centraliseContentPanel.setLayout(new GridLayout(count, 1, 0, 0));
                contentArea = new JTextArea[count];
                //
                userLogList = new String[count];
                populateUserLog("select counter from useractivitymonitor "
                        + "where activitydate like '%" + (month + " " + year) + "%'");
                //
                promptLab.setText(" " + count + " Activities Tracked For Period Specified.");
                //
                for (int pos = 0; pos < count; pos++) {
                    contentArea[pos] = new JTextArea(
                            getOneDetail("select activityperformed from useractivitymonitor "
                            + "where counter = '" + userLogList[pos] + "'"));
                    contentArea[pos].setEditable(false);
                    contentArea[pos].setFont(new Font(getFontType, Font.PLAIN, 12));
                    contentArea[pos].addMouseListener(new displayUserProfile(pos, count));
                    contentArea[pos].setBorder(BorderFactory.createEtchedBorder());
                    contentArea[pos].setForeground(Color.BLACK);
                    contentArea[pos].setLineWrap(true);
                    contentArea[pos].setWrapStyleWord(true);
                    contentArea[pos].setToolTipText("Click For More Info...");
                    //
                    centraliseContentPanel.add(contentArea[pos]);
                }
                validate();
            }
        }
        //

        class displayUserProfile extends MouseAdapter {

            int pos = 0,
                    count = 0;

            public displayUserProfile(int p, int c) {
                pos = p;
                count = c;
            }

            public void mouseClicked(MouseEvent e) {
                for (int rw = 0; rw < count; rw++) {
                    contentArea[rw].setForeground(Color.BLACK);
                }
                //
                contentArea[pos].setForeground(Color.RED);
                promptLab.setText("Login Name: "
                        + getOneDetail("select username from useractivitymonitor "
                        + "where counter = '" + userLogList[pos] + "'").toUpperCase() + ", "
                        + "Login Date/Time: "
                        + getOneDetail("select activitydate from useractivitymonitor "
                        + "where counter = '" + userLogList[pos] + "'"));
                //
                JOptionPane.showMessageDialog(null,
                        promptLab,
                        getSchoolName.toUpperCase(),
                        JOptionPane.INFORMATION_MESSAGE,
                        infoIcon);
            }
        }
        //

        void populateUserLog(String query) {
            int res = 0;
            Connection conn;
            Statement statement;
            ResultSet set;
            try {
                conn = createConnection();
                statement = conn.createStatement();
                set = statement.executeQuery(query);
                //
                if (!set.next()) {
                    return;
                } 
                else {
                    ResultSetMetaData data = set.getMetaData();
                    Vector row = new Vector(),
                            column = new Vector();
                    //
                    for (int x = 1; x <= data.getColumnCount(); x++) {
                        column.addElement(data.getColumnName(x));
                    }
                    do {
                        row.addElement(getNextRow(set, data));
                        userLogList[res] = row.get(res).toString().substring(1,
                                row.get(res).toString().length() - 1);
                        res++;
                    } while (set.next());
                    //
                    validate();
                }
            } catch (SQLException sql) {
            } catch (Exception gen) {
            }
        }
    }
    //
    static class viewRemarkDialog extends JDialog {

        JTextArea remarkArea = new JTextArea();
        JPanel areaPanel = new JPanel();
        //

        public viewRemarkDialog(String m) {
            remarkArea.setText(m);
            //
            remarkArea.setEditable(false);
            remarkArea.setFont(new Font(getFontType, Font.PLAIN, 12));
            remarkArea.setForeground(Color.BLACK);
            remarkArea.setBorder(BorderFactory.createEtchedBorder());
            remarkArea.setLineWrap(true);
            remarkArea.setWrapStyleWord(true);
            //
            areaPanel.setLayout(new BorderLayout());
            areaPanel.add(new JScrollPane(remarkArea), BorderLayout.CENTER);
            //
            add(areaPanel);
            validate();
        }
    }
    //
    String letterMonth(int mon) {
        String rep = "";
        if (mon == 1) {
            rep = "january";
        } else if (mon == 2) {
            rep = "february";
        } else if (mon == 3) {
            rep = "march";
        } else if (mon == 4) {
            rep = "april";
        } else if (mon == 5) {
            rep = "may";
        } else if (mon == 6) {
            rep = "june";
        } else if (mon == 7) {
            rep = "july";
        } else if (mon == 8) {
            rep = "august";
        } else if (mon == 9) {
            rep = "september";
        } else if (mon == 10) {
            rep = "october";
        } else if (mon == 11) {
            rep = "november";
        } else {
            rep = "december";
        }
        //
        return rep;
    }
    //
    String nuberOfDayInMonth(String mon) {
        String rep = "";
        if (mon.equalsIgnoreCase("sep")
                || mon.equalsIgnoreCase("apr")
                || mon.equalsIgnoreCase("jun")
                || mon.equalsIgnoreCase("nov")) {
            rep = "30";
        } else if (mon.equalsIgnoreCase("feb")) {
            rep = "28";
        } else {
            rep = "31";
        }
        //
        return rep;
    }
    //
    int getNumberOfDetails(String query) {
        //method responsible for query the database
        Connection conn;
        Statement statement;
        ResultSet set;
        int rep = 0;
        try {
            conn = createConnection();
            statement = conn.createStatement();
            set = statement.executeQuery(query);
            //
            if (!set.next()) {
                return 0;
            } else {
                ResultSetMetaData data = set.getMetaData();
                Vector row = new Vector(), column = new Vector();
                //
                for (int x = 1; x <= data.getColumnCount(); x++) {
                    column.addElement(data.getColumnName(x));
                }
                do {
                    row.addElement(getNextRow(set, data));
                } while (set.next());
                JTable tab = new JTable(row, column);
                rep = tab.getRowCount();
            }
        } catch (SQLException sql) {
        } catch (Exception gen) {
        }
        return rep;
    }
    //connection string to mysql database
    String getOneDetail(String query) {
        //method responsible for query the database
        Connection conn = null;
        Statement statement = null;
        ResultSet set = null;
        String rep = "";
        try {
            conn = createConnection();
            statement = conn.createStatement();
            set = statement.executeQuery(query);
            //
            if (!set.next()) {
                //promptLab.setText(" No Record(s) Found.");
                return "";
            } 
            else {
                ResultSetMetaData data = set.getMetaData();
                Vector row = new Vector(),
                        column = new Vector();
                //
                for (int x = 1; x <= data.getColumnCount(); x++) {
                    column.addElement(data.getColumnName(x));
                }
                do {
                    row.addElement(getNextRow(set, data));
                    rep = row.get(0).toString().substring(1,
                            row.get(0).toString().length() - 1);
                } while (set.next());
                //
                validate();
                conn.close();
            }
        } catch (SQLException sql) {
        } catch (Exception gen) {
        }
        return rep;
    }
    //
    public Connection createConnection() {
        Connection connection = null;
        String url = "jdbc:odbc:" + databaseName;
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            connection = DriverManager.getConnection(url, "emmanuel", "orioncorporation");
        } catch (SQLException sql) {
        } catch (Exception gen) {
        }
        //
        return connection;
    }
    //insert, update and delete method
    public int insertUpdateDelete(String query) {
        int res = 0;
        Connection connection = createConnection();
        try {
            Statement statement = connection.createStatement();
            res = statement.executeUpdate(query);
            connection.commit();
        } catch (SQLException sql) {
        } catch (Exception gen) {
        }
        //
        return res;
    }
    //
    public Vector getNextRow(ResultSet rs, ResultSetMetaData data) throws SQLException {
        Vector currentRow = new Vector();
        for (int x = 1; x <= data.getColumnCount(); x++) {
            switch (data.getColumnType(x)) {
                case Types.VARCHAR:
                    currentRow.addElement(rs.getString(x));
                    break;
                case Types.INTEGER:
                    currentRow.addElement(new Long(rs.getLong(x)));
                    break;
                default:
                    break;
            }
        }
        return currentRow;
    }
    //
    public static void main(String[] args) {
        promptLab.setFont(new Font(getFontType, Font.BOLD, 10));
        promptLab.setForeground(Color.RED);
        //
        resWizard = new CBTSystemClientVersion();
        Dimension frame = resWizard.getToolkit().getScreenSize();
        resWizard.setSize(frame.width, frame.height);
        resWizard.setResizable(false);
        resWizard.setTitle(titleText);
        resWizard.setVisible(true);
        resWizard.setIconImage(
                Toolkit.getDefaultToolkit().getImage("reflection.gif"));
        //
        resWizard.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                promptLab.setText("Shutting Down Service?");
                int confirm = JOptionPane.showConfirmDialog(null,
                        promptLab,
                        getSchoolName.toUpperCase(),
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        questionIcon);
                //
                if (confirm == JOptionPane.YES_OPTION) {
                    resWizard.setDefaultCloseOperation(resWizard.EXIT_ON_CLOSE);
                } else {
                    resWizard.setDefaultCloseOperation(resWizard.DO_NOTHING_ON_CLOSE);
                }
            }
        });
    }
}
