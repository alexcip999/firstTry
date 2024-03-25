import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Screen extends JFrame {
    private JPanel panelTop;
    private JPanel panelRight;
    private JPanel panelLeft;
    private JList ListPeople;
    private JButton ButtonNew;
    private JButton ButtonSave;
    private JTextField textName;
    private JTextField textEmail;
    private JTextField textPhoneNumber;
    private JTextField textAddress;
    private JLabel labelAge;
    private JTextField textDateOfBirth;
    private JPanel panelMain;
    private JButton removeFromListButton;
    private ArrayList<Person> people;
    private DefaultListModel listPeopleModel;

    Screen(){
        super("My fancy contacts project");
        this.setContentPane(this.panelMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        people = new ArrayList<Person>();
        listPeopleModel = new DefaultListModel();
        ListPeople.setModel(listPeopleModel);
        ButtonSave.setEnabled(false);

        ButtonNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                    Person p = new Person(
                            textName.getText(),
                            textEmail.getText(),
                            textPhoneNumber.getText(),
                            textDateOfBirth.getText(),
                            textAddress.getText()
                    );
            boolean existingPerson = verifyExistingPerson(p);

                    if(!existingPerson){
                        people.add(p);
                        refreshListPeople();
                    }else{
                        System.out.println("Persoana exista deja in lista");
                    }
                }
        });
        ButtonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int personNumber = ListPeople.getSelectedIndex();
                if(personNumber >= 0){
                    Person p = people.get(personNumber);
                    p.setName(textName.getText());
                    p.setEmail(textEmail.getText());
                    p.setPhoneNumber(textPhoneNumber.getText());
                    p.setDateOfBirth(textDateOfBirth.getText());
                    p.setAddress(textAddress.getText());
                    refreshListPeople();
                }

            }
        });
        ListPeople.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int personNumber = ListPeople.getSelectedIndex();
                if(personNumber >= 0){
                    Person p = people.get(personNumber);
                    textName.setText(p.getName());
                    textEmail.setText(p.getEmail());
                    textPhoneNumber.setText(p.getPhoneNumber());
                    textDateOfBirth.setText(p.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    textAddress.setText(p.getAddress());
                    labelAge.setText(Integer.toString(p.getAge()) + "years");
                    ButtonSave.setEnabled(true);
                }else{
                    ButtonSave.setEnabled(false);
                }
            }
        });
        removeFromListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int personIndex = ListPeople.getSelectedIndex();
                if(personIndex >= 0){
                    people.remove(personIndex);
                    refreshListPeople();
                    removeFromListButton.setEnabled(true);
                }else{
                    removeFromListButton.setEnabled(false);
                }

            }
        });
    }

    public void refreshListPeople(){
        listPeopleModel.removeAllElements();
        System.out.println("Removing all people from list");
        for(Person p : people){
            System.out.println("Adding person to list");
            listPeopleModel.addElement(p.getName());
        }
    }
    public void addPerson(Person p){
        people.add(p);
        refreshListPeople();
    }

    public boolean verifyExistingPerson(Person p){
        for(Person person : people){
            if(person.getName().equals(p.getName())){
                return true;
            }
        }
        return false;
    }



    public static void main(String[] args) {
        Screen screen = new Screen();
        screen.setVisible(true);
//        Person andrei = new Person("Andrei Hirlav", "andrei@gmail.com", "0700 509", "22/10/2000");
//        Person vali = new Person("Valentin Ungureanu", "vali@gmail.com", "0300 500", "05/09/2003");
//        Person maria = new Person("Achirei Maria", "maria@gmail.com", "0789 600", "23/04/2003");
//
//        screen.addPerson(andrei);
//        screen.addPerson(vali);
//        screen.addPerson(maria);
    }

}
