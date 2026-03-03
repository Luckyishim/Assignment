package gui.admin;

import models.Administrator;
import models.Scheduler;
import utils.FileHandler;
import utils.UserFileHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SchedulerManagementFrame extends JFrame {

    private Administrator admin;
    private JTable table;
    private DefaultTableModel tableModel;
    private List<Scheduler> schedulers;

    public SchedulerManagementFrame(Administrator admin) {
        this.admin = admin;

        setTitle("Scheduler Staff Management");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // filter
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField searchField = new JTextField(15);
        JButton searchBtn = new JButton("Search by Name");
        topPanel.add(new JLabel("Search:"));
        topPanel.add(searchField);
        topPanel.add(searchBtn);
        add(topPanel, BorderLayout.NORTH);

        // table
        String[] cols = {"User ID", "Staff ID", "Name", "Email", "Phone"};
        tableModel = new DefaultTableModel(cols, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // buttons
        JPanel bottomPanel = new JPanel(new FlowLayout());
        JButton addBtn = new JButton("Add Scheduler");
        JButton editBtn = new JButton("Edit Selected");
        JButton deleteBtn = new JButton("Delete Selected");
        JButton backBtn = new JButton("Back");

        bottomPanel.add(addBtn);
        bottomPanel.add(editBtn);
        bottomPanel.add(deleteBtn);
        bottomPanel.add(backBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        loadSchedulers(null);

        searchBtn.addActionListener(e -> loadSchedulers(searchField.getText().trim()));
        addBtn.addActionListener(e -> showAddDialog());
        editBtn.addActionListener(e -> showEditDialog());
        deleteBtn.addActionListener(e -> handleDelete());
        backBtn.addActionListener(e -> {
            dispose();
            new AdminDashboard(admin);
        });

        setVisible(true);
    }

    private void loadSchedulers(String nameFilter) {
        tableModel.setRowCount(0);
        schedulers = UserFileHandler.getAllSchedulers();
        for (Scheduler s : schedulers) {
            if (nameFilter != null && !nameFilter.isEmpty() &&
                    !s.getName().toLowerCase().contains(nameFilter.toLowerCase())) continue;

            tableModel.addRow(new Object[]{
                    s.getUserId(), s.getStaffId(), s.getName(), s.getEmail(), s.getPhone()
            });
        }
    }

    private void showAddDialog() {
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JPasswordField passField = new JPasswordField();
        JTextField phoneField = new JTextField();
        JTextField staffIdField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        panel.add(new JLabel("Name:")); panel.add(nameField);
        panel.add(new JLabel("Email:")); panel.add(emailField);
        panel.add(new JLabel("Password:")); panel.add(passField);
        panel.add(new JLabel("Phone:")); panel.add(phoneField);
        panel.add(new JLabel("Staff ID:")); panel.add(staffIdField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add Scheduler", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String pass = new String(passField.getPassword()).trim();
            String phone = phoneField.getText().trim();
            String staffId = staffIdField.getText().trim();

            if (name.isEmpty() || email.isEmpty() || pass.isEmpty() || phone.isEmpty() || staffId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required.");
                return;
            }

            if (UserFileHandler.emailExists(email)) {
                JOptionPane.showMessageDialog(this, "Email already in use.");
                return;
            }

            String userId = FileHandler.generateId("data/users.txt", "SCR");
            Scheduler s = new Scheduler(userId, staffId, name, email, pass, phone);
            UserFileHandler.saveUser(s);
            JOptionPane.showMessageDialog(this, "Scheduler added!");
            loadSchedulers(null);
        }
    }

    private void showEditDialog() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a scheduler to edit.");
            return;
        }

        Scheduler selected = schedulers.get(selectedRow);

        JTextField nameField = new JTextField(selected.getName());
        JTextField phoneField = new JTextField(selected.getPhone());

        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        panel.add(new JLabel("Name:")); panel.add(nameField);
        panel.add(new JLabel("Phone:")); panel.add(phoneField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Edit Scheduler", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            selected.setName(nameField.getText().trim());
            selected.setPhone(phoneField.getText().trim());
            UserFileHandler.updateUser(selected);
            JOptionPane.showMessageDialog(this, "Scheduler updated!");
            loadSchedulers(null);
        }
    }

    private void handleDelete() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a scheduler to delete.");
            return;
        }

        Scheduler selected = schedulers.get(selectedRow);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Delete scheduler: " + selected.getName() + "?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            UserFileHandler.deleteUser(selected.getUserId());
            JOptionPane.showMessageDialog(this, "Scheduler deleted.");
            loadSchedulers(null);
        }
    }
}