package gui.scheduler;

import models.Hall;
import models.HallSchedule;
import models.Scheduler;
import utils.FileHandler;
import utils.HallFileHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class HallScheduleFrame extends JFrame {

    private Scheduler scheduler;
    private JTable table;
    private DefaultTableModel tableModel;
    private List<HallSchedule> schedules;

    public HallScheduleFrame(Scheduler scheduler) {
        this.scheduler = scheduler;

        setTitle("Hall Schedule Management");
        setSize(750, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // table
        String[] cols = {"Schedule ID", "Hall ID", "Type", "Start", "End"};
        tableModel = new DefaultTableModel(cols, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // buttons
        JPanel bottomPanel = new JPanel(new FlowLayout());
        JButton addAvailBtn = new JButton("Add Availability");
        JButton addMaintBtn = new JButton("Add Maintenance");
        JButton deleteBtn = new JButton("Delete Selected");
        JButton backBtn = new JButton("Back");

        bottomPanel.add(addAvailBtn);
        bottomPanel.add(addMaintBtn);
        bottomPanel.add(deleteBtn);
        bottomPanel.add(backBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        loadSchedules();

        addAvailBtn.addActionListener(e -> showAddDialog("AVAILABILITY"));
        addMaintBtn.addActionListener(e -> showAddDialog("MAINTENANCE"));
        deleteBtn.addActionListener(e -> handleDelete());
        backBtn.addActionListener(e -> {
            dispose();
            new SchedulerDashboard(scheduler);
        });

        setVisible(true);
    }

    private void loadSchedules() {
        tableModel.setRowCount(0);
        schedules = HallFileHandler.getAllSchedules();
        for (HallSchedule s : schedules) {
            tableModel.addRow(new Object[]{
                    s.getScheduleId(), s.getHallId(), s.getScheduleType(),
                    s.getStartDateTime(), s.getEndDateTime()
            });
        }
    }

    private void showAddDialog(String type) {
        List<Hall> halls = HallFileHandler.getAllHalls();
        if (halls.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No halls found. Please add halls first.");
            return;
        }

        String[] hallOptions = new String[halls.size()];
        for (int i = 0; i < halls.size(); i++) {
            hallOptions[i] = halls.get(i).getHallId() + " - " + halls.get(i).getHallName();
        }

        JComboBox<String> hallBox = new JComboBox<>(hallOptions);
        JTextField startField = new JTextField("dd-MM-yyyy HH:mm");
        JTextField endField = new JTextField("dd-MM-yyyy HH:mm");

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.add(new JLabel("Select Hall:"));
        panel.add(hallBox);
        panel.add(new JLabel("Start (dd-MM-yyyy HH:mm):"));
        panel.add(startField);
        panel.add(new JLabel("End (dd-MM-yyyy HH:mm):"));
        panel.add(endField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add " + type + " Schedule", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String start = startField.getText().trim();
            String end = endField.getText().trim();

            if (start.isEmpty() || end.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Start and end date/time are required.");
                return;
            }

            String hallId = halls.get(hallBox.getSelectedIndex()).getHallId();
            String scheduleId = FileHandler.generateId("txt-data/schedules.txt", "SCH");

            HallSchedule schedule = new HallSchedule(scheduleId, hallId, type, start, end);
            HallFileHandler.saveSchedule(schedule);
            JOptionPane.showMessageDialog(this, type + " schedule added!");
            loadSchedules();
        }
    }

    private void handleDelete() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a schedule to delete.");
            return;
        }

        HallSchedule selected = schedules.get(selectedRow);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Delete schedule: " + selected.getScheduleId() + "?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            HallFileHandler.deleteSchedule(selected.getScheduleId());
            JOptionPane.showMessageDialog(this, "Schedule deleted.");
            loadSchedules();
        }
    }
}