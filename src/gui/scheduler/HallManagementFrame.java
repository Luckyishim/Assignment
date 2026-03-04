package gui.scheduler;

import models.Hall;
import models.Scheduler;
import utils.FileHandler;
import utils.HallFileHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class HallManagementFrame extends JFrame {

    private Scheduler scheduler;
    private JTable table;
    private DefaultTableModel tableModel;
    private List<Hall> halls;

    public HallManagementFrame(Scheduler scheduler) {
        this.scheduler = scheduler;

        setTitle("Hall Management");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // filter panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Filter by type:"));
        JComboBox<String> filterBox = new JComboBox<>(new String[]{"ALL", "AUDITORIUM", "BANQUET_HALL", "MEETING_ROOM"});
        topPanel.add(filterBox);
        JButton filterBtn = new JButton("Filter");
        topPanel.add(filterBtn);
        add(topPanel, BorderLayout.NORTH);

        // table
        String[] cols = {"Hall ID", "Name", "Type", "Capacity", "Rate/hr"};
        tableModel = new DefaultTableModel(cols, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // buttons
        JPanel bottomPanel = new JPanel(new FlowLayout());
        JButton addBtn = new JButton("Add Hall");
        JButton editBtn = new JButton("Edit Selected");
        JButton deleteBtn = new JButton("Delete Selected");
        JButton backBtn = new JButton("Back");

        bottomPanel.add(addBtn);
        bottomPanel.add(editBtn);
        bottomPanel.add(deleteBtn);
        bottomPanel.add(backBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        loadHalls("ALL");

        filterBtn.addActionListener(e -> loadHalls((String) filterBox.getSelectedItem()));
        addBtn.addActionListener(e -> showAddDialog());
        editBtn.addActionListener(e -> showEditDialog());
        deleteBtn.addActionListener(e -> handleDelete());
        backBtn.addActionListener(e -> {
            dispose();
            new SchedulerDashboard(scheduler);
        });

        setVisible(true);
    }

    private void loadHalls(String filter) {
        tableModel.setRowCount(0);
        if (filter.equals("ALL")) {
            halls = HallFileHandler.getAllHalls();
        } else {
            halls = HallFileHandler.getHallsByType(filter);
        }
        for (Hall h : halls) {
            tableModel.addRow(new Object[]{
                    h.getHallId(), h.getHallName(), h.getHallType(), h.getCapacity(), "RM " + h.getRatePerHour()
            });
        }
    }

    private void showAddDialog() {
        JTextField nameField = new JTextField();
        JComboBox<String> typeBox = new JComboBox<>(new String[]{"AUDITORIUM", "BANQUET_HALL", "MEETING_ROOM"});

        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        panel.add(new JLabel("Hall Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Hall Type:"));
        panel.add(typeBox);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add New Hall", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Hall name is required.");
                return;
            }
            String hallId = FileHandler.generateId("txt-data/halls.txt", "HAL");
            Hall hall = new Hall(hallId, name, (String) typeBox.getSelectedItem());
            HallFileHandler.saveHall(hall);
            JOptionPane.showMessageDialog(this, "Hall added successfully!");
            loadHalls("ALL");
        }
    }

    private void showEditDialog() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a hall to edit.");
            return;
        }

        Hall selected = halls.get(selectedRow);

        JTextField nameField = new JTextField(selected.getHallName());
        JComboBox<String> typeBox = new JComboBox<>(new String[]{"AUDITORIUM", "BANQUET_HALL", "MEETING_ROOM"});
        typeBox.setSelectedItem(selected.getHallType());

        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        panel.add(new JLabel("Hall Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Hall Type:"));
        panel.add(typeBox);

        int result = JOptionPane.showConfirmDialog(this, panel, "Edit Hall", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Hall name is required.");
                return;
            }
            selected.setHallName(name);
            selected.setHallType((String) typeBox.getSelectedItem());
            HallFileHandler.updateHall(selected);
            JOptionPane.showMessageDialog(this, "Hall updated!");
            loadHalls("ALL");
        }
    }

    private void handleDelete() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a hall to delete.");
            return;
        }

        Hall selected = halls.get(selectedRow);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Delete hall: " + selected.getHallName() + "?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            HallFileHandler.deleteHall(selected.getHallId());
            JOptionPane.showMessageDialog(this, "Hall deleted.");
            loadHalls("ALL");
        }
    }
}