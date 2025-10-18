
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.time.*;
import java.time.format.TextStyle;
import java.util.*;

public class AplikasiPerhitunganHari extends JFrame {

    private JComboBox<String> comboBulan;
    private JSpinner spinnerTahun;
    private JButton tombolHitung;
    private JTextArea areaHasil;
    private JButton tombolSelisih;
    private JSpinner spinnerTanggal1;
    private JSpinner spinnerTanggal2;

    public AplikasiPerhitunganHari() {
        setTitle("Aplikasi Perhitungan Hari");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 450);
        setLayout(new BorderLayout());

        JPanel panelInput = new JPanel();
        panelInput.setLayout(new GridLayout(5, 2, 10, 10));

        // ComboBox untuk bulan
        panelInput.add(new JLabel("Pilih Bulan:"));
        comboBulan = new JComboBox<>(new String[]{
            "Januari", "Februari", "Maret", "April", "Mei", "Juni",
            "Juli", "Agustus", "September", "Oktober", "November", "Desember"
        });
        panelInput.add(comboBulan);

        // Spinner untuk tahun
        panelInput.add(new JLabel("Masukkan Tahun:"));
        spinnerTahun = new JSpinner(new SpinnerNumberModel(LocalDate.now().getYear(), 1, 9999, 1));
        panelInput.add(spinnerTahun);

        // Tombol hitung jumlah hari
        tombolHitung = new JButton("Hitung Jumlah Hari");
        panelInput.add(tombolHitung);

        // Spinner tanggal untuk fitur selisih
        panelInput.add(new JLabel("Tanggal 1:"));
        spinnerTanggal1 = new JSpinner(new SpinnerDateModel());
        panelInput.add(spinnerTanggal1);

        panelInput.add(new JLabel("Tanggal 2:"));
        spinnerTanggal2 = new JSpinner(new SpinnerDateModel());
        panelInput.add(spinnerTanggal2);

        tombolSelisih = new JButton("Hitung Selisih Hari");
        panelInput.add(tombolSelisih);

        add(panelInput, BorderLayout.NORTH);

        // Area hasil
        areaHasil = new JTextArea();
        areaHasil.setEditable(false);
        add(new JScrollPane(areaHasil), BorderLayout.CENTER);

        // Event untuk tombol hitung jumlah hari
        tombolHitung.addActionListener(e -> hitungJumlahHari());

        // Event untuk tombol selisih hari
        tombolSelisih.addActionListener(e -> hitungSelisihHari());

        // Event perubahan tahun
        spinnerTahun.addChangeListener(e -> areaHasil.setText(""));
    }

    private void hitungJumlahHari() {
        int bulan = comboBulan.getSelectedIndex() + 1;
        int tahun = (int) spinnerTahun.getValue();

        YearMonth ym = YearMonth.of(tahun, bulan);
        int jumlahHari = ym.lengthOfMonth();

        LocalDate hariPertama = ym.atDay(1);
        LocalDate hariTerakhir = ym.atEndOfMonth();

        String namaBulan = ym.getMonth().getDisplayName(TextStyle.FULL, new Locale("id", "ID"));
        String hasil = "Bulan: " + namaBulan + " " + tahun +
                "\nJumlah Hari: " + jumlahHari +
                "\nHari pertama: " + hariPertama.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("id", "ID")) +
                "\nHari terakhir: " + hariTerakhir.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("id", "ID"));

        areaHasil.setText(hasil);
    }

    private void hitungSelisihHari() {
        Date tanggal1 = (Date) spinnerTanggal1.getValue();
        Date tanggal2 = (Date) spinnerTanggal2.getValue();

        LocalDate tgl1 = tanggal1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate tgl2 = tanggal2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        long selisih = Math.abs(Duration.between(tgl1.atStartOfDay(), tgl2.atStartOfDay()).toDays());
        areaHasil.setText("Selisih antara kedua tanggal adalah " + selisih + " hari.");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AplikasiPerhitunganHari frame = new AplikasiPerhitunganHari();
            frame.setVisible(true);
        });
    }
}
