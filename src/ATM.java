package ATM;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;


//ATM class
//the main DEMO class
public class ATM {

    private JFrame frmDalhousieAtm;
    private JTextField accountField;
    private JPasswordField passwordField;
    private JTextField amountField;

    private static ATMSystem DalATM;

    //card and password that the user try to input
    private String card;
    private String tempPassword = "";


    //record the number of times that user try to login, if it reaches 3, end the program
    private int temptToLogin;

    //if the user asks for a receipt, set askForReceipt to true.
    private boolean askForReceipt;

    //record an action that the user want to do
    private static String action = "";

    //record an amount that an transaction will need
    private double amount;
    //this otherAmount is for number pad input
    private String otherAmount = "";

    //record accounts that an transaction will need
    private Account fromAccount;
    private Account toAccount;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {

        //create a customer to the system
        Customer john = new Customer("John", "9999", "1234");

        //set its chequing account balance to 500
        john.getChequingAccount().setBalance(500);

        //initializing a new ATMSystem
        DalATM = new ATMSystem();

        //add john to ATMSystem
        DalATM.addCustomer(john);


        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ATM window = new ATM();
                    window.frmDalhousieAtm.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
    }

    /**
     * Create the application.
     */
    public ATM() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmDalhousieAtm = new JFrame();
        frmDalhousieAtm.setTitle("Dalhousie ATM");
        frmDalhousieAtm.setBounds(100, 100, 450, 300);
        frmDalhousieAtm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmDalhousieAtm.getContentPane().setLayout(new CardLayout(0, 0));

        askForReceipt = false;


        JPanel login = new JPanel();
        JPanel AskForReceipt = new JPanel();
        JPanel mainMenu = new JPanel();
        JPanel AmountSelection = new JPanel();
        JPanel AnotherTransaction = new JPanel();

        JPanel WithdrawPage = new JPanel();
        JPanel DepositPage = new JPanel();
        JPanel TransferFromPage = new JPanel();
        JPanel TransferToPage = new JPanel();
        JPanel BalancePage = new JPanel();
        JPanel OtherAmountPage = new JPanel();

        JButton TransferToPageChequing = new JButton("Chequing");
        JButton TransferToPageSaving = new JButton("Saving");


        //creat an actionlistener if the user choose  chequing account
        ActionListener chooseChequingAccount = new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (action.equals("Withdraw")) {
                    fromAccount = DalATM.getCustomer(card).getChequingAccount();
                    WithdrawPage.setVisible(false);
                    AmountSelection.setVisible(true);
                } else if (action.equals("Deposit")) {
                    DepositPage.setVisible(false);
                    AmountSelection.setVisible(true);
                    toAccount = DalATM.getCustomer(card).getChequingAccount();
                } else if (action.equals("TransferFrom")) {
                    TransferFromPage.setVisible(false);
                    TransferToPage.setVisible(true);
                    fromAccount = DalATM.getCustomer(card).getChequingAccount();

                    TransferToPageChequing.setVisible(false);

                    setAction("TransferTo");
                } else if (action.equals("TransferTo")) {
                    TransferToPage.setVisible(false);
                    AmountSelection.setVisible(true);
                    toAccount = DalATM.getCustomer(card).getChequingAccount();

                }

            }
        };

        //creat an actionlistener if the user choose saving account
        ActionListener chooseSavingAccount = new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (action.equals("Withdraw")) {
                    WithdrawPage.setVisible(false);
                    AmountSelection.setVisible(true);
                    fromAccount = DalATM.getCustomer(card).getSavingAccount();
                } else if (action.equals("Deposit")) {
                    DepositPage.setVisible(false);
                    AmountSelection.setVisible(true);
                    toAccount = DalATM.getCustomer(card).getSavingAccount();
                } else if (action.equals("TransferFrom")) {
                    TransferFromPage.setVisible(false);
                    TransferToPage.setVisible(true);
                    fromAccount = DalATM.getCustomer(card).getSavingAccount();

                    TransferToPageSaving.setVisible(false);

                    setAction("TransferTo");
                } else if (action.equals("TransferTo")) {
                    TransferToPage.setVisible(false);
                    AmountSelection.setVisible(true);
                    toAccount = DalATM.getCustomer(card).getSavingAccount();

                }

            }
        };


        ActionListener transActions = new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (action.equals("Withdraw"))
                    DalATM.getCustomer(card).withdraw(fromAccount, amount);

                if (action.equals("Deposit"))
                    DalATM.getCustomer(card).deposit(toAccount, amount);

//				if(action.equals("Transfer"))

            }
        };


        //creating an acionlistener ,if user want to exit
        //that show a panel that asks the user want to do another action
        ActionListener exitButton = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainMenu.setVisible(false);
                DepositPage.setVisible(false);
                WithdrawPage.setVisible(false);
                TransferFromPage.setVisible(false);
                TransferToPage.setVisible(false);
                BalancePage.setVisible(false);
                AmountSelection.setVisible(false);

                AnotherTransaction.setVisible(true);
            }
        };

        //creating an acionlistener, if user dont want to do any other action
        //show receipt if user asks for it
        ActionListener exitSystem = new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (askForReceipt)
                    System.out.println(DalATM.getCustomer(card).getFinalReceipt());

                System.exit(0);
            }
        };


        frmDalhousieAtm.getContentPane().add(login, "name_2069495476215951");
        login.setLayout(new BorderLayout(0, 0));
        //set login visible at first
        login.setVisible(true);


        JLabel lblPleaseEnterYour = new JLabel("Please enter your card number and use num pad to input password");
        lblPleaseEnterYour.setBorder(new EmptyBorder(5, 10, 5, 10));
        lblPleaseEnterYour.setHorizontalAlignment(SwingConstants.CENTER);
        login.add(lblPleaseEnterYour, BorderLayout.NORTH);

        JPanel input = new JPanel();
        login.add(input, BorderLayout.CENTER);
        input.setLayout(null);

        JLabel lblClientNumber = new JLabel("Card number:");
        lblClientNumber.setHorizontalAlignment(SwingConstants.RIGHT);
        lblClientNumber.setBounds(101, 4, 80, 22);
        input.add(lblClientNumber);

        accountField = new JTextField();
        accountField.setBounds(191, 5, 74, 21);
        input.add(accountField);
        accountField.setColumns(10);

        JLabel lblNewLabel = new JLabel("Password:");
        lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNewLabel.setBounds(101, 36, 84, 15);
        input.add(lblNewLabel);

        passwordField = new JPasswordField();
        passwordField.setEditable(false);
        passwordField.setBounds(191, 33, 74, 21);
        input.add(passwordField);

        JButton loginPageExit = new JButton("Exit");
        loginPageExit.addActionListener(exitSystem);
        loginPageExit.setBounds(377, 214, 57, 22);
        input.add(loginPageExit);

        JPanel panel_19 = new JPanel();
        panel_19.setBounds(115, 57, 227, 156);
        input.add(panel_19);
        panel_19.setLayout(new GridLayout(4, 3, 0, 0));

        JButton button = new JButton("7");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tempPassword += 7;
                passwordField.setText(tempPassword);
            }
        });
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(Color.WHITE);
        panel_19.add(button);

        JButton button_2 = new JButton("8");
        button_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tempPassword += 8;
                passwordField.setText(tempPassword);
            }
        });
        button_2.setFont(new Font("Arial", Font.BOLD, 16));
        button_2.setBackground(Color.WHITE);
        panel_19.add(button_2);

        JButton button_10 = new JButton("9");
        button_10.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tempPassword += 9;
                passwordField.setText(tempPassword);
            }
        });
        button_10.setFont(new Font("Arial", Font.BOLD, 16));
        button_10.setBackground(Color.WHITE);
        panel_19.add(button_10);

        JButton button_11 = new JButton("4");
        button_11.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tempPassword += 4;
                passwordField.setText(tempPassword);
            }
        });
        button_11.setFont(new Font("Arial", Font.BOLD, 16));
        button_11.setBackground(Color.WHITE);
        panel_19.add(button_11);

        JButton button_16 = new JButton("5");
        button_16.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tempPassword += 5;
                passwordField.setText(tempPassword);
            }
        });
        button_16.setFont(new Font("Arial", Font.BOLD, 16));
        button_16.setBackground(Color.WHITE);
        panel_19.add(button_16);

        JButton button_17 = new JButton("6");
        button_17.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tempPassword += 6;
                passwordField.setText(tempPassword);
            }
        });
        button_17.setFont(new Font("Arial", Font.BOLD, 16));
        button_17.setBackground(Color.WHITE);
        panel_19.add(button_17);

        JButton button_20 = new JButton("1");
        button_20.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tempPassword += 1;
                passwordField.setText(tempPassword);
            }
        });
        button_20.setFont(new Font("Arial", Font.BOLD, 16));
        button_20.setBackground(Color.WHITE);
        panel_19.add(button_20);

        JButton button_21 = new JButton("2");
        button_21.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tempPassword += 2;
                passwordField.setText(tempPassword);
            }
        });
        button_21.setFont(new Font("Arial", Font.BOLD, 16));
        button_21.setBackground(Color.WHITE);
        panel_19.add(button_21);

        JButton button_22 = new JButton("3");
        button_22.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tempPassword += 3;
                passwordField.setText(tempPassword);
            }
        });
        button_22.setFont(new Font("Arial", Font.BOLD, 16));
        button_22.setBackground(Color.WHITE);
        panel_19.add(button_22);

        JButton button_23 = new JButton("Corr.");
        button_23.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tempPassword = "";
                passwordField.setText("");
            }
        });
        button_23.setFont(new Font("Arial", Font.BOLD, 16));
        button_23.setBackground(Color.WHITE);
        panel_19.add(button_23);

        JButton button_24 = new JButton("0");
        button_24.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tempPassword += 0;
                passwordField.setText(tempPassword);
            }
        });
        button_24.setFont(new Font("Arial", Font.BOLD, 16));
        button_24.setBackground(Color.WHITE);
        panel_19.add(button_24);

        JButton button_25 = new JButton("Enter");
        button_25.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                card = accountField.getText();
                char[] inputPassword = passwordField.getPassword();
                String realPassword = "";

                //get a real password from password field
                for (int i = 0; i < inputPassword.length; i++)
                    realPassword += inputPassword[i];

                //if the card number or the password is wrong
                if (!ATMSystem.isCorrectAccount(card, realPassword)) {
                    //show error message that if user's input is wrong
                    if (temptToLogin != 2) {
                        temptToLogin++;
                        JOptionPane.showMessageDialog(null, "Incorrect card number or password. Please try again. You have " + (3 - temptToLogin) + " chance to login.");
                    }
                    //if the user ran out of chances
                    else {
                        JOptionPane.showMessageDialog(null, "You ran out of chances. Sorry, bye! ");
                        System.exit(0);
                    }
                }

                //if the card and password are correct
                else {
                    DalATM.getCustomer(card).login();
                    JOptionPane.showMessageDialog(null, "Welcome! " + DalATM.getCustomer(card));
                    login.setVisible(false);
                    AskForReceipt.setVisible(true);
                }

                passwordField.setText("");
                tempPassword = "";
            }
        });
        button_25.setFont(new Font("Arial", Font.BOLD, 16));
        button_25.setBackground(Color.WHITE);
        panel_19.add(button_25);


        frmDalhousieAtm.getContentPane().add(AskForReceipt, "name_2113776273365927");
        AskForReceipt.setLayout(new BorderLayout(0, 0));

        JLabel lblWouldYouLike = new JLabel("Would you like to receive a receipt?");
        lblWouldYouLike.setHorizontalAlignment(SwingConstants.CENTER);
        lblWouldYouLike.setBorder(new EmptyBorder(5, 0, 5, 0));
        AskForReceipt.add(lblWouldYouLike, BorderLayout.NORTH);

        JPanel panel_9 = new JPanel();
        AskForReceipt.add(panel_9, BorderLayout.EAST);
        panel_9.setLayout(new GridLayout(5, 1, 0, 0));

        JButton YesReceipt = new JButton("Yes");
        YesReceipt.setPreferredSize(new Dimension(81, 31));
        YesReceipt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                askForReceipt = true;
                AskForReceipt.setVisible(false);
                mainMenu.setVisible(true);
            }

        });
        panel_9.add(YesReceipt);

        JButton NoReceipt = new JButton("No");
        NoReceipt.setPreferredSize(new Dimension(81, 31));
        NoReceipt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AskForReceipt.setVisible(false);
                mainMenu.setVisible(true);
            }

        });
        panel_9.add(NoReceipt);

        JPanel panel_5 = new JPanel();
        AskForReceipt.add(panel_5, BorderLayout.SOUTH);
        panel_5.setLayout(new BorderLayout(0, 0));

        JButton AskForReceiptPageExit = new JButton("Exit");
        AskForReceiptPageExit.addActionListener(exitSystem);
        panel_5.add(AskForReceiptPageExit, BorderLayout.EAST);


        frmDalhousieAtm.getContentPane().add(mainMenu, "name_2069499174719267");
        mainMenu.setLayout(new BorderLayout(0, 0));

        JLabel mainMenuMessage = new JLabel("Which transaction do you like to do?");
        mainMenuMessage.setBorder(new EmptyBorder(5, 0, 5, 0));
        mainMenuMessage.setHorizontalAlignment(SwingConstants.CENTER);
        mainMenu.add(mainMenuMessage, BorderLayout.NORTH);


        JPanel panel = new JPanel();
        mainMenu.add(panel, BorderLayout.EAST);
        panel.setLayout(new GridLayout(5, 1, 0, 0));

        JButton Withdraw = new JButton("Withdraw");
        Withdraw.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setAction("Withdraw");
                mainMenu.setVisible(false);
                WithdrawPage.setVisible(true);
            }

        });

        panel.add(Withdraw);

        JButton Deposit = new JButton("Deposit");
        Deposit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setAction("Deposit");
                mainMenu.setVisible(false);
                DepositPage.setVisible(true);
            }

        });
        panel.add(Deposit);

        JPanel panel_3 = new JPanel();
        mainMenu.add(panel_3, BorderLayout.WEST);
        panel_3.setLayout(new GridLayout(5, 1, 0, 0));

        JButton Transfer = new JButton("Transfer");
        Transfer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setAction("TransferFrom");
                mainMenu.setVisible(false);
                TransferFromPage.setVisible(true);
                TransferToPageChequing.setVisible(true);
                TransferToPageSaving.setVisible(true);
            }

        });
        panel_3.add(Transfer);

        JButton Balance = new JButton("Balance");
        Balance.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setAction("Balance");
                mainMenu.setVisible(false);
                BalancePage.setVisible(true);
            }

        });
        panel_3.add(Balance);

        JPanel panel_1 = new JPanel();
        mainMenu.add(panel_1, BorderLayout.SOUTH);
        panel_1.setLayout(new BorderLayout(0, 0));

        JButton btnExitMainMenu = new JButton("Exit");
        btnExitMainMenu.addActionListener(exitButton);
        panel_1.add(btnExitMainMenu, BorderLayout.EAST);


        frmDalhousieAtm.getContentPane().add(AmountSelection, "name_2112228350886938");
        AmountSelection.setLayout(new BorderLayout(0, 0));

        JLabel AmountPageLabel = new JLabel("Please select the amount");
        AmountPageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        AmountPageLabel.setBorder(new EmptyBorder(5, 0, 5, 0));
        AmountSelection.add(AmountPageLabel, BorderLayout.NORTH);

        JPanel AmountSelectionLeft = new JPanel();
        AmountSelection.add(AmountSelectionLeft, BorderLayout.EAST);
        AmountSelectionLeft.setLayout(new GridLayout(5, 1, 0, 0));

        JButton dollars40 = new JButton("$ 40");
        dollars40.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                amount = 40;
                ATMrun(fromAccount, toAccount, action, amount);
                AmountSelection.setVisible(false);
                AnotherTransaction.setVisible(true);
            }
        });
        AmountSelectionLeft.add(dollars40);

        JButton dollars80 = new JButton("$ 80");
        dollars80.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                amount = 80;
                ATMrun(fromAccount, toAccount, action, amount);
                AmountSelection.setVisible(false);
                AnotherTransaction.setVisible(true);
            }
        });
        AmountSelectionLeft.add(dollars80);

        JButton dollars200 = new JButton("$ 200");
        dollars200.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                amount = 200;
                ATMrun(fromAccount, toAccount, action, amount);
                AmountSelection.setVisible(false);
                AnotherTransaction.setVisible(true);
            }
        });
        AmountSelectionLeft.add(dollars200);

        JPanel AmountSelectionRight = new JPanel();
        AmountSelection.add(AmountSelectionRight, BorderLayout.WEST);
        AmountSelectionRight.setLayout(new GridLayout(5, 1, 0, 0));

        JButton dollars20 = new JButton("$ 20");
        dollars20.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                amount = 20;
                ATMrun(fromAccount, toAccount, action, amount);
                AmountSelection.setVisible(false);
                AnotherTransaction.setVisible(true);
            }
        });
        AmountSelectionRight.add(dollars20);

        JButton dollars60 = new JButton("$ 60");
        dollars60.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                amount = 60;
                ATMrun(fromAccount, toAccount, action, amount);
                AmountSelection.setVisible(false);
                AnotherTransaction.setVisible(true);
            }
        });
        AmountSelectionRight.add(dollars60);

        JButton dollars100 = new JButton("$ 100");
        dollars100.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                amount = 100;
                ATMrun(fromAccount, toAccount, action, amount);
                AmountSelection.setVisible(false);
                AnotherTransaction.setVisible(true);
            }
        });
        AmountSelectionRight.add(dollars100);

        JButton AmountOthers = new JButton("Other");
        AmountOthers.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AmountSelection.setVisible(false);
                OtherAmountPage.setVisible(true);
                otherAmount = "";
                amountField.setText("");
            }
        });
        AmountSelectionRight.add(AmountOthers);

        JPanel AmountSelectionBottom = new JPanel();
        AmountSelection.add(AmountSelectionBottom, BorderLayout.SOUTH);
        AmountSelectionBottom.setLayout(new BorderLayout(0, 0));

        JButton button_4 = new JButton("Exit");
        button_4.addActionListener(exitButton);
        AmountSelectionBottom.add(button_4, BorderLayout.EAST);

        JButton button_5 = new JButton("Back");
        button_5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AmountSelection.setVisible(false);
                mainMenu.setVisible(true);
            }
        });
        AmountSelectionBottom.add(button_5, BorderLayout.WEST);


        frmDalhousieAtm.getContentPane().add(AnotherTransaction, "name_2113174120177280");
        AnotherTransaction.setLayout(new BorderLayout(0, 0));

        JLabel label_2 = new JLabel("Would you like to do another transaction?");
        label_2.setHorizontalAlignment(SwingConstants.CENTER);
        label_2.setBorder(new EmptyBorder(5, 0, 5, 0));
        AnotherTransaction.add(label_2, BorderLayout.NORTH);

        JPanel panel_6 = new JPanel();
        AnotherTransaction.add(panel_6, BorderLayout.EAST);
        panel_6.setLayout(new GridLayout(5, 1, 0, 0));

        JButton YesAnotherTransaction = new JButton("Yes");
        YesAnotherTransaction.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AnotherTransaction.setVisible(false);
                mainMenu.setVisible(true);
            }
        });
        YesAnotherTransaction.setPreferredSize(new Dimension(81, 31));
        panel_6.add(YesAnotherTransaction);

        JButton NoAnotherTransaction = new JButton("No");
        NoAnotherTransaction.addActionListener(exitSystem);
        NoAnotherTransaction.setPreferredSize(new Dimension(81, 31));
        panel_6.add(NoAnotherTransaction);

        JPanel panel_7 = new JPanel();
        AnotherTransaction.add(panel_7, BorderLayout.SOUTH);
        panel_7.setLayout(new BorderLayout(0, 0));

        JButton AnotherTransactionPageExit = new JButton("Exit");
        AnotherTransactionPageExit.addActionListener(exitSystem);
        panel_7.add(AnotherTransactionPageExit, BorderLayout.EAST);


        frmDalhousieAtm.getContentPane().add(WithdrawPage, "name_2125318842081698");
        WithdrawPage.setLayout(new BorderLayout(0, 0));

        JLabel label = new JLabel("Which account do you like to withdraw from?");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(new EmptyBorder(5, 0, 5, 0));
        WithdrawPage.add(label, BorderLayout.NORTH);

        JPanel panel_8 = new JPanel();
        WithdrawPage.add(panel_8, BorderLayout.EAST);
        panel_8.setLayout(new GridLayout(5, 1, 0, 0));

        JButton button_1 = new JButton("Chequing");
        button_1.addActionListener(chooseChequingAccount);
        panel_8.add(button_1);

        JButton button_3 = new JButton("Saving");
        //button_3.addActionListener(chooseSavingAccount);
        button_3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(null, "Withdrawing from Saving account will charge you $2, continue?", "Notification", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.YES_OPTION) {
                    WithdrawPage.setVisible(false);
                    AmountSelection.setVisible(true);
                    fromAccount = DalATM.getCustomer(card).getSavingAccount();
                }

            }
        });
        panel_8.add(button_3);

        JPanel panel_10 = new JPanel();
        WithdrawPage.add(panel_10, BorderLayout.SOUTH);
        panel_10.setLayout(new BorderLayout(0, 0));

        JButton button_7 = new JButton("Back");
        button_7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                WithdrawPage.setVisible(false);
                mainMenu.setVisible(true);
            }
        });
        panel_10.add(button_7, BorderLayout.WEST);

        JButton button_6 = new JButton("Exit");
        button_6.addActionListener(exitButton);
        panel_10.add(button_6, BorderLayout.EAST);

        frmDalhousieAtm.getContentPane().add(DepositPage, "name_2125601640996956");
        DepositPage.setLayout(new BorderLayout(0, 0));

        JLabel label_1 = new JLabel("Which account do you like to deposit to?");
        label_1.setHorizontalAlignment(SwingConstants.CENTER);
        label_1.setBorder(new EmptyBorder(5, 0, 5, 0));
        DepositPage.add(label_1, BorderLayout.NORTH);

        JPanel panel_12 = new JPanel();
        DepositPage.add(panel_12, BorderLayout.EAST);
        panel_12.setLayout(new GridLayout(5, 1, 0, 0));

        JButton button_8 = new JButton("Chequing");
        button_8.addActionListener(chooseChequingAccount);
        panel_12.add(button_8);

        JButton button_9 = new JButton("Saving");
        button_9.addActionListener(chooseSavingAccount);
        panel_12.add(button_9);

        JPanel panel_13 = new JPanel();
        DepositPage.add(panel_13, BorderLayout.SOUTH);
        panel_13.setLayout(new BorderLayout(0, 0));

        JButton DepositPageExit = new JButton("Exit");
        DepositPageExit.addActionListener(exitButton);
        panel_13.add(DepositPageExit, BorderLayout.EAST);

        JButton DepositPageBack = new JButton("Back");
        DepositPageBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DepositPage.setVisible(false);
                mainMenu.setVisible(true);
            }
        });
        panel_13.add(DepositPageBack, BorderLayout.WEST);

        frmDalhousieAtm.getContentPane().add(TransferFromPage, "name_2125722967184063");
        TransferFromPage.setLayout(new BorderLayout(0, 0));

        JLabel TransferFromMessage = new JLabel("Which account do you like to transfer from?");
        TransferFromMessage.setHorizontalAlignment(SwingConstants.CENTER);
        TransferFromMessage.setBorder(new EmptyBorder(5, 0, 5, 0));
        TransferFromPage.add(TransferFromMessage, BorderLayout.NORTH);

        JPanel panel_14 = new JPanel();
        TransferFromPage.add(panel_14, BorderLayout.EAST);
        panel_14.setLayout(new GridLayout(5, 1, 0, 0));

        JButton button_12 = new JButton("Chequing");
        button_12.addActionListener(chooseChequingAccount);
        panel_14.add(button_12);

        JButton button_13 = new JButton("Saving");
        button_13.addActionListener(chooseSavingAccount);
        panel_14.add(button_13);

        JPanel panel_15 = new JPanel();
        TransferFromPage.add(panel_15, BorderLayout.SOUTH);
        panel_15.setLayout(new BorderLayout(0, 0));

        JButton button_14 = new JButton("Exit");
        button_14.addActionListener(exitButton);
        panel_15.add(button_14, BorderLayout.EAST);

        JButton button_15 = new JButton("Back");
        button_15.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TransferFromPage.setVisible(false);
                mainMenu.setVisible(true);
            }
        });
        panel_15.add(button_15, BorderLayout.WEST);

        frmDalhousieAtm.getContentPane().add(TransferToPage, "name_2125865920949762");
        TransferToPage.setLayout(new BorderLayout(0, 0));

        JLabel label_3 = new JLabel("Which account do you like to fransfer to?");
        label_3.setHorizontalAlignment(SwingConstants.CENTER);
        label_3.setBorder(new EmptyBorder(5, 0, 5, 0));
        TransferToPage.add(label_3, BorderLayout.NORTH);

        JPanel panel_16 = new JPanel();
        TransferToPage.add(panel_16, BorderLayout.EAST);
        panel_16.setLayout(new GridLayout(5, 1, 0, 0));


        TransferToPageChequing.addActionListener(chooseChequingAccount);
        panel_16.add(TransferToPageChequing);


        TransferToPageSaving.addActionListener(chooseSavingAccount);

        panel_16.add(TransferToPageSaving);

        JPanel panel_17 = new JPanel();
        TransferToPage.add(panel_17, BorderLayout.SOUTH);
        panel_17.setLayout(new BorderLayout(0, 0));

        JButton button_18 = new JButton("Back");
        button_18.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TransferToPage.setVisible(false);
                mainMenu.setVisible(true);
            }
        });
        panel_17.add(button_18, BorderLayout.WEST);

        JButton button_19 = new JButton("Exit");
        button_19.addActionListener(exitButton);
        panel_17.add(button_19, BorderLayout.EAST);


        frmDalhousieAtm.getContentPane().add(BalancePage, "name_2126196277511431");
        BalancePage.setLayout(new BorderLayout(0, 0));

        JLabel label_4 = new JLabel("Which account do you like to check?");
        label_4.setHorizontalAlignment(SwingConstants.CENTER);
        label_4.setBorder(new EmptyBorder(5, 0, 5, 0));
        BalancePage.add(label_4, BorderLayout.NORTH);

        JPanel panel_11 = new JPanel();
        BalancePage.add(panel_11, BorderLayout.EAST);
        panel_11.setLayout(new GridLayout(5, 1, 0, 0));

        JButton checkChequing = new JButton("Chequing");
        checkChequing.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Your chequing account balance : $" + DalATM.getCustomer(card).getChequingAccount());
            }
        });
        panel_11.add(checkChequing);

        JButton checkSaving = new JButton("Saving");
        checkSaving.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Your saving account balance : $" + DalATM.getCustomer(card).getSavingAccount());
            }
        });
        panel_11.add(checkSaving);

        JPanel panel_18 = new JPanel();
        BalancePage.add(panel_18, BorderLayout.SOUTH);
        panel_18.setLayout(new BorderLayout(0, 0));

        JButton BalancePageBack = new JButton("Back");
        BalancePageBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                BalancePage.setVisible(false);
                mainMenu.setVisible(true);
            }
        });
        panel_18.add(BalancePageBack, BorderLayout.WEST);

        JButton BalancePageExit = new JButton("Exit");
        panel_18.add(BalancePageExit, BorderLayout.EAST);


        frmDalhousieAtm.getContentPane().add(OtherAmountPage, "name_2135402658981191");
        OtherAmountPage.setLayout(new BorderLayout(0, 0));

        JLabel lblPleaseTypeA = new JLabel("Choose your amount");
        lblPleaseTypeA.setBorder(new EmptyBorder(5, 0, 5, 0));
        lblPleaseTypeA.setHorizontalAlignment(SwingConstants.CENTER);
        OtherAmountPage.add(lblPleaseTypeA, BorderLayout.NORTH);

        JPanel OtherAmountPageTextPanel = new JPanel();
        OtherAmountPage.add(OtherAmountPageTextPanel, BorderLayout.CENTER);
        OtherAmountPageTextPanel.setLayout(null);

        amountField = new JTextField();
        amountField.setEditable(false);
        amountField.setBounds(177, 10, 66, 21);
        OtherAmountPageTextPanel.add(amountField);
        amountField.setColumns(10);

        JPanel panel_2 = new JPanel();
        panel_2.setBounds(99, 40, 228, 163);
        OtherAmountPageTextPanel.add(panel_2);
        panel_2.setLayout(new GridLayout(4, 3, 0, 0));

        JButton btnNewButton = new JButton("7");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                otherAmount += 7;
                amountField.setText(otherAmount);
            }
        });
        btnNewButton.setFont(new Font("Arial", Font.BOLD, 16));
        btnNewButton.setBackground(Color.WHITE);
        panel_2.add(btnNewButton);

        JButton btnNewButton_1 = new JButton("8");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                otherAmount += 8;
                amountField.setText(otherAmount);
            }
        });
        btnNewButton_1.setFont(new Font("Arial", Font.BOLD, 16));
        btnNewButton_1.setBackground(Color.WHITE);
        panel_2.add(btnNewButton_1);

        JButton btnNewButton_2 = new JButton("9");
        btnNewButton_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                otherAmount += 9;
                amountField.setText(otherAmount);
            }
        });
        btnNewButton_2.setFont(new Font("Arial", Font.BOLD, 16));
        btnNewButton_2.setBackground(Color.WHITE);
        panel_2.add(btnNewButton_2);

        JButton btnNewButton_3 = new JButton("4");
        btnNewButton_3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                otherAmount += 4;
                amountField.setText(otherAmount);
            }
        });
        btnNewButton_3.setFont(new Font("Arial", Font.BOLD, 16));
        btnNewButton_3.setBackground(Color.WHITE);
        panel_2.add(btnNewButton_3);

        JButton btnNewButton_4 = new JButton("5");
        btnNewButton_4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                otherAmount += 5;
                amountField.setText(otherAmount);
            }
        });
        btnNewButton_4.setFont(new Font("Arial", Font.BOLD, 16));
        btnNewButton_4.setBackground(Color.WHITE);
        panel_2.add(btnNewButton_4);

        JButton btnNewButton_5 = new JButton("6");
        btnNewButton_5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                otherAmount += 6;
                amountField.setText(otherAmount);
            }
        });
        btnNewButton_5.setFont(new Font("Arial", Font.BOLD, 16));
        btnNewButton_5.setBackground(Color.WHITE);
        panel_2.add(btnNewButton_5);

        JButton btnNewButton_6 = new JButton("1");
        btnNewButton_6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                otherAmount += 1;
                amountField.setText(otherAmount);
            }
        });
        btnNewButton_6.setFont(new Font("Arial", Font.BOLD, 16));
        btnNewButton_6.setBackground(Color.WHITE);
        panel_2.add(btnNewButton_6);

        JButton btnNewButton_7 = new JButton("2");
        btnNewButton_7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                otherAmount += 2;
                amountField.setText(otherAmount);
            }
        });
        btnNewButton_7.setFont(new Font("Arial", Font.BOLD, 16));
        btnNewButton_7.setBackground(Color.WHITE);
        panel_2.add(btnNewButton_7);

        JButton btnNewButton_8 = new JButton("3");
        btnNewButton_8.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                otherAmount += 3;
                amountField.setText(otherAmount);
            }
        });
        btnNewButton_8.setFont(new Font("Arial", Font.BOLD, 16));
        btnNewButton_8.setBackground(Color.WHITE);
        panel_2.add(btnNewButton_8);

        JButton btnNewButton_9 = new JButton("0");
        btnNewButton_9.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                otherAmount += 0;
                amountField.setText(otherAmount);
            }
        });


        JButton btnNewButton_10 = new JButton("Corr.");
        btnNewButton_10.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                otherAmount = "";
                amountField.setText("");
            }
        });

        btnNewButton_10.setFont(new Font("Arial", Font.BOLD, 16));
        btnNewButton_10.setBackground(Color.WHITE);
        panel_2.add(btnNewButton_10);
        btnNewButton_9.setFont(new Font("Arial", Font.BOLD, 16));
        btnNewButton_9.setBackground(Color.WHITE);
        panel_2.add(btnNewButton_9);


        JButton btnNewButton_11 = new JButton("Enter");
        btnNewButton_11.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {
                    amount = Integer.parseInt(amountField.getText());
                    if (amount > 1000 && (action.equals("Withdraw") || action.equals("Deposit")))
                        JOptionPane.showMessageDialog(null, "Invalid amount, we only accept an amount below 1000.");
                    else {
                        ATMrun(fromAccount, toAccount, action, amount);
                        amountField.setText("");
                        OtherAmountPage.setVisible(false);
                        AnotherTransaction.setVisible(true);
                    }
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(null, "Invalid amount, we only accept an amount below 1000.");
                }

            }

        });
        btnNewButton_11.setFont(new Font("Arial", Font.BOLD, 16));
        btnNewButton_11.setBackground(Color.WHITE);
        panel_2.add(btnNewButton_11);

        JPanel panel_4 = new JPanel();
        OtherAmountPage.add(panel_4, BorderLayout.SOUTH);
        panel_4.setLayout(new BorderLayout(0, 0));

        JButton OtherAmountPageExit = new JButton("Exit");
        OtherAmountPageExit.addActionListener(exitButton);
        panel_4.add(OtherAmountPageExit, BorderLayout.EAST);

        JButton OtherAmountPageBack = new JButton("Back");
        OtherAmountPageBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OtherAmountPage.setVisible(false);
                AmountSelection.setVisible(true);
            }
        });
        panel_4.add(OtherAmountPageBack, BorderLayout.WEST);
        BalancePageExit.addActionListener(exitButton);
    }

    //method that set an action
    public void setAction(String a) {

        if (a.equals("Withdraw")) {
            action = "Withdraw";
        }
        if (a.equals("Deposit")) {
            action = "Deposit";
        }
        if (a.equals("Balance")) {
            action = "Balance";
        }
        if (a.equals("TransferFrom")) {
            action = "TransferFrom";
        }
        if (a.equals("TransferTo")) {
            action = "TransferTo";
        }
    }

    //do the action which will use variables too finish a transaction
    public void ATMrun(Account from, Account to, String a, double amount) {

        if (a.equals("Withdraw")) {
            if (from.getAccountName().equals("Saving") && (amount + 2) > from.getBalance())
                JOptionPane.showMessageDialog(null, "You dont have a such money after charging the service fee.");
            else
                DalATM.getCustomer(card).withdraw(from, amount);
        }
        if (a.equals("Deposit")) {
            DalATM.getCustomer(card).deposit(to, amount);
        }
        if (a.equals("TransferTo")) {
            DalATM.getCustomer(card).transferFrom(from, to, amount);
        }


    }
}
