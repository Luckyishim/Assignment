package gui.admin;

import models.Administrator;
import models.Customer;
import utils.UserFileHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UserManagementFrame extends JFrame {

    private Administrator admin;
    private JTable table;
    private DefaultTableModel tableModel;
    private List<Customer> customers;

    public UserManagementFrame(Administrator admin) {
        this.admin = admin;

        setTitle("User Management");
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
        String[] cols = {"User ID", "Name", "Email", "Phone", "Address", "Blocked"};
        tableModel = new DefaultTableModel(cols, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // buttons
        JPanel bottomPanel = new JPanel(new FlowLayout());
        JButton blockBtn = new JButton("Block/Unblock Selected");
        JButton deleteBtn = new JButton("Delete Selected");
        JButton backBtn = new JButton("Back");

        bottomPanel.add(blockBtn);
        bottomPanel.add(deleteBtn);
        bottomPanel.add(backBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        loadCustomers(null);

        searchBtn.addActionListener(e -> loadCustomers(searchField.getText().trim()));
        blockBtn.addActionListener(e -> handleBlock());
        deleteBtn.addActionListener(e -> handleDelete());
        backBtn.addActionListener(e -> {
            dispose();
            new AdminDashboard(admin);
        });

        setVisible(true);
    }

    private void loadCustomers(String nameFilter) {
        tableModel.setRowCount(0);
        customers = UserFileHandler.getAllCustomers();
        for (Customer c : customers) {
            if (nameFilter != null && !nameFilter.isEmpty() &&
                    !c.getName().toLowerCase().contains(nameFilter.toLowerCase())) continue;

            tableModel.addRow(new Object[]{
                    c.getUserId(), c.getName(), c.getEmail(), c.getPhone(), c.getAddress(), c.isBlocked()
            });
        }
    }

    private void handleBlock() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user.");
            return;
        }

        Customer selected = customers.get(selectedRow);
        selected.setBlocked(!selected.isBlocked()); // toggle block
        UserFileHandler.updateUser(selected);
        JOptionPane.showMessageDialog(this, selected.getName() + " is now " + (selected.isBlocked() ? "BLOCKED" : "UNBLOCKED"));
        loadCustomers(null);
    }

    private void handleDelete() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to delete.");
            return;
        }

        Customer selected = customers.get(selectedRow);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Delete user: " + selected.getName() + "?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            UserFileHandler.deleteUser(selected.getUserId());
            JOptionPane.showMessageDialog(this, "User deleted.");
            loadCustomers(null);
        }
    }
}