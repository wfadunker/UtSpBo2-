/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crud;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import java.sql.ResultSetMetaData;
/**
 *
 * @author User
 */
    public class crud {
    private Connection Koneksidb;
    private String username="root";
    private String password="";
    private String dbname="db_pertanian"; 
    private String urlKoneksi="jdbc:mysql://localhost/"+dbname;
    public boolean duplikasi=false;

    public String CEK_KATEGORI_KAT = null;
    public String CEK_NAMA_BARANG, CEK_KATEGORI_BRG, CEK_HARGA_PETANI, CEK_HARGA_PENGEPUL, CEK_HARGA_GROSIR, CEK_HARGA_KONSUMEN = null;
    public String CEK_NAMASERVER, CEK_IP, CEK_ALAMAT_SERVER = null;
    public String CEK_ALAMAT_ID = null;

    
    public crud(){
        try {
            Driver dbdriver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(dbdriver);
            Koneksidb=DriverManager.getConnection(urlKoneksi,username,password);
            System.out.print("Database Terkoneksi");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.toString());
        }
    }

    public void simpanKategori01(String Index_Kategori, String Kategori){
        try {
            String sqlsimpan="insert into Kategori(Index_Kategori, Kategori) value"
                    + " ('"+Index_Kategori+"', '"+Kategori+"')";
            String sqlcari="select*from Kategori where Index_Kategori='"+Index_Kategori+"'";
            
            Statement cari=Koneksidb.createStatement();
            ResultSet data=cari.executeQuery(sqlcari);
            
            if (data.next()){
                JOptionPane.showMessageDialog(null, "Index Kategori sudah terdaftar");
            } else {
                Statement perintah=Koneksidb.createStatement();
                perintah.execute(sqlsimpan);
                JOptionPane.showMessageDialog(null, "Data Kategori berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void simpanKategori02(String Index_Kategori, String Kategori){
        try {
            String sqlsimpan="INSERT INTO Kategori (Index_Kategori, Kategori) VALUES (?, ?)";
            String sqlcari= "SELECT*FROM Kategori WHERE Index_Kategori = ?";
            
            PreparedStatement cari = Koneksidb.prepareStatement(sqlcari);
            cari.setString(1, Index_Kategori);
            ResultSet data = cari.executeQuery();
            
            if (data.next()){
                JOptionPane.showMessageDialog(null, "Index Kategori sudah terdaftar");
                this.duplikasi = true;
                this.CEK_KATEGORI_KAT = data.getString("Kategori");
            } else {
                this.duplikasi = false;
                this.CEK_KATEGORI_KAT = null;
                
                PreparedStatement perintah = Koneksidb.prepareStatement(sqlsimpan);
                perintah.setString(1, Index_Kategori);
                perintah.setString(2, Kategori);
                perintah.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Kategori berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void ubahKategori(String Index_Kategori, String Kategori){
        try {
            String sqlubah="UPDATE Kategori SET Kategori = ? WHERE Index_Kategori = ?";
            PreparedStatement perintah = Koneksidb.prepareStatement(sqlubah);
            perintah.setString(1, Kategori);
            perintah.setString(2, Index_Kategori); 
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Kategori berhasil diubah");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void hapusKategori(String Index_Kategori){
        try {
            String sqlhapus="DELETE FROM Kategori WHERE Index_Kategori = ?";
            PreparedStatement perintah = Koneksidb.prepareStatement(sqlhapus);
            perintah.setString(1, Index_Kategori);
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Kategori berhasil dihapus");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void tampilDataKategori(JTable komponentabel, String SQL){
        try {
            PreparedStatement perintah = Koneksidb.prepareStatement(SQL);
            ResultSet data = perintah.executeQuery();
            ResultSetMetaData meta = data.getMetaData();
            int jumlahkolom = meta.getColumnCount();
            DefaultTableModel modeltabel = new DefaultTableModel();
            
            modeltabel.addColumn("Index Kategori");
            modeltabel.addColumn("Kategori");
            
            while(data.next()){
                Object[] row = new Object[jumlahkolom];
                for(int i=1; i<=jumlahkolom; i++){
                    row[i-1]=data.getObject(i);
                }
                modeltabel.addRow(row);
            }
            komponentabel.setModel(modeltabel);
        } catch (Exception e) {
        }
    }

    public void simpanDataBarang01(String Kode_Barang, String Nama_barang, String Kategori, String Harga_petani, String Harga_pengepul, String Harga_grosir, String Harga_konsumen){
        try {
            String sqlsimpan="insert into Data_Barang(Kode_Barang, Nama_barang, Kategori, Harga_petani, Harga_pengepul, Harga_grosir, Harga_konsumen) value"
                    + " ('"+Kode_Barang+"', '"+Nama_barang+"', '"+Kategori+"', '"+Harga_petani+"', '"+Harga_pengepul+"', '"+Harga_grosir+"', '"+Harga_konsumen+"')";
            String sqlcari="select*from Data_Barang where Kode_Barang='"+Kode_Barang+"'";
            
            Statement cari=Koneksidb.createStatement();
            ResultSet data=cari.executeQuery(sqlcari);
            
            if (data.next()){
                JOptionPane.showMessageDialog(null, "Kode Barang sudah terdaftar");
            } else {
                Statement perintah=Koneksidb.createStatement();
                perintah.execute(sqlsimpan);
                JOptionPane.showMessageDialog(null, "Data Barang berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void simpanDataBarang02(String Kode_Barang, String Nama_barang, String Kategori, String Harga_petani, String Harga_pengepul, String Harga_grosir, String Harga_konsumen){
        try {
            String sqlsimpan="INSERT INTO Data_Barang (Kode_Barang, Nama_barang, Kategori, Harga_petani, Harga_pengepul, Harga_grosir, Harga_konsumen) VALUES (?, ?, ?, ?, ?, ?, ?)";
            String sqlcari= "SELECT*FROM Data_Barang WHERE Kode_Barang = ?";
            
            PreparedStatement cari = Koneksidb.prepareStatement(sqlcari);
            cari.setString(1, Kode_Barang);
            ResultSet data = cari.executeQuery();
            
            if (data.next()){
                JOptionPane.showMessageDialog(null, "Kode Barang sudah terdaftar");
                this.duplikasi = true;
                this.CEK_NAMA_BARANG = data.getString("Nama_barang");
                this.CEK_KATEGORI_BRG = data.getString("Kategori");
                this.CEK_HARGA_PETANI = data.getString("Harga_petani");
                this.CEK_HARGA_PENGEPUL = data.getString("Harga_pengepul");
                this.CEK_HARGA_GROSIR = data.getString("Harga_grosir");
                this.CEK_HARGA_KONSUMEN = data.getString("Harga_konsumen");
            } else {
                this.duplikasi = false;
                this.CEK_NAMA_BARANG = null;
                this.CEK_KATEGORI_BRG = null;
                this.CEK_HARGA_PETANI = null;
                this.CEK_HARGA_PENGEPUL = null;
                this.CEK_HARGA_GROSIR = null;
                this.CEK_HARGA_KONSUMEN = null;
                
                PreparedStatement perintah = Koneksidb.prepareStatement(sqlsimpan);
                perintah.setString(1, Kode_Barang);
                perintah.setString(2, Nama_barang);
                perintah.setString(3, Kategori);
                perintah.setString(4, Harga_petani);
                perintah.setString(5, Harga_pengepul);
                perintah.setString(6, Harga_grosir);
                perintah.setString(7, Harga_konsumen);
                perintah.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Barang berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void ubahDataBarang(String Kode_Barang, String Nama_barang, String Kategori, String Harga_petani, String Harga_pengepul, String Harga_grosir, String Harga_konsumen){
        try {
            String sqlubah="UPDATE Data_Barang SET Nama_barang = ?, Kategori = ?, Harga_petani = ?, Harga_pengepul = ?, Harga_grosir = ?, Harga_konsumen = ? WHERE Kode_Barang = ?";
            PreparedStatement perintah = Koneksidb.prepareStatement(sqlubah);
            perintah.setString(1, Nama_barang);
            perintah.setString(2, Kategori);
            perintah.setString(3, Harga_petani);
            perintah.setString(4, Harga_pengepul);
            perintah.setString(5, Harga_grosir);
            perintah.setString(6, Harga_konsumen);
            perintah.setString(7, Kode_Barang); 
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Barang berhasil diubah");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void hapusDataBarang(String Kode_Barang){
        try {
            String sqlhapus="DELETE FROM Data_Barang WHERE Kode_Barang = ?";
            PreparedStatement perintah = Koneksidb.prepareStatement(sqlhapus);
            perintah.setString(1, Kode_Barang);
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Barang berhasil dihapus");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void tampilDataBarang(JTable komponentabel, String SQL){
        try {
            PreparedStatement perintah = Koneksidb.prepareStatement(SQL);
            ResultSet data = perintah.executeQuery();
            ResultSetMetaData meta = data.getMetaData();
            int jumlahkolom = meta.getColumnCount();
            DefaultTableModel modeltabel = new DefaultTableModel();
            
            modeltabel.addColumn("Kode Barang");
            modeltabel.addColumn("Nama Barang");
            modeltabel.addColumn("Kategori");
            modeltabel.addColumn("Harga Petani");
            modeltabel.addColumn("Harga Pengepul");
            modeltabel.addColumn("Harga Grosir");
            modeltabel.addColumn("Harga Konsumen");
            
            while(data.next()){
                Object[] row = new Object[jumlahkolom];
                for(int i=1; i<=jumlahkolom; i++){
                    row[i-1]=data.getObject(i);
                }
                modeltabel.addRow(row);
            }
            komponentabel.setModel(modeltabel);
        } catch (Exception e) {
        }
    }

    public void simpanDataServer01(String Index_Server, String NamaServer, String IP, String Alamat){
        try {
            String sqlsimpan="insert into Data_server(Index_Server, NamaServer, IP, Alamat) value"
                    + " ('"+Index_Server+"', '"+NamaServer+"', '"+IP+"', '"+Alamat+"')";
            String sqlcari="select*from Data_server where Index_Server='"+Index_Server+"'";
            
            Statement cari=Koneksidb.createStatement();
            ResultSet data=cari.executeQuery(sqlcari);
            
            if (data.next()){
                JOptionPane.showMessageDialog(null, "Index Server sudah terdaftar");
            } else {
                Statement perintah=Koneksidb.createStatement();
                perintah.execute(sqlsimpan);
                JOptionPane.showMessageDialog(null, "Data Server berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void simpanDataServer02(String Index_Server, String NamaServer, String IP, String Alamat){
        try {
            String sqlsimpan="INSERT INTO Data_server (Index_Server, NamaServer, IP, Alamat) VALUES (?, ?, ?, ?)";
            String sqlcari= "SELECT*FROM Data_server WHERE Index_Server = ?";
            
            PreparedStatement cari = Koneksidb.prepareStatement(sqlcari);
            cari.setString(1, Index_Server);
            ResultSet data = cari.executeQuery();
            
            if (data.next()){
                JOptionPane.showMessageDialog(null, "Index Server sudah terdaftar");
                this.duplikasi = true;
                this.CEK_NAMASERVER = data.getString("NamaServer");
                this.CEK_IP = data.getString("IP");
                this.CEK_ALAMAT_SERVER = data.getString("Alamat");
            } else {
                this.duplikasi = false;
                this.CEK_NAMASERVER = null;
                this.CEK_IP = null;
                this.CEK_ALAMAT_SERVER = null;
                
                PreparedStatement perintah = Koneksidb.prepareStatement(sqlsimpan);
                perintah.setString(1, Index_Server);
                perintah.setString(2, NamaServer);
                perintah.setString(3, IP);
                perintah.setString(4, Alamat);
                perintah.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Server berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void ubahDataServer(String Index_Server, String NamaServer, String IP, String Alamat){
        try {
            String sqlubah="UPDATE Data_server SET NamaServer = ?, IP = ?, Alamat = ? WHERE Index_Server = ?";
            PreparedStatement perintah = Koneksidb.prepareStatement(sqlubah);
            perintah.setString(1, NamaServer);
            perintah.setString(2, IP);
            perintah.setString(3, Alamat);
            perintah.setString(4, Index_Server); // ID sebagai parameter terakhir
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Server berhasil diubah");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void hapusDataServer(String Index_Server){
        try {
            String sqlhapus="DELETE FROM Data_server WHERE Index_Server = ?";
            PreparedStatement perintah = Koneksidb.prepareStatement(sqlhapus);
            perintah.setString(1, Index_Server);
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Server berhasil dihapus");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void tampilDataServer(JTable komponentabel, String SQL){
        try {
            PreparedStatement perintah = Koneksidb.prepareStatement(SQL);
            ResultSet data = perintah.executeQuery();
            ResultSetMetaData meta = data.getMetaData();
            int jumlahkolom = meta.getColumnCount();
            DefaultTableModel modeltabel = new DefaultTableModel();
            
            modeltabel.addColumn("Index Server");
            modeltabel.addColumn("Nama Server");
            modeltabel.addColumn("IP");
            modeltabel.addColumn("Alamat");
            
            while(data.next()){
                Object[] row = new Object[jumlahkolom];
                for(int i=1; i<=jumlahkolom; i++){
                    row[i-1]=data.getObject(i);
                }
                modeltabel.addRow(row);
            }
            komponentabel.setModel(modeltabel);
        } catch (Exception e) {
        }
    }

    public void simpanIDServer01(String Nama, String Alamat){
        try {
            String sqlsimpan="insert into ID_Server(Nama, Alamat) value"
                    + " ('"+Nama+"', '"+Alamat+"')";
            String sqlcari="select*from ID_Server where Nama='"+Nama+"'";
            
            Statement cari=Koneksidb.createStatement();
            ResultSet data=cari.executeQuery(sqlcari);
            
            if (data.next()){
                JOptionPane.showMessageDialog(null, "Nama ID Server sudah terdaftar");
            } else {
                Statement perintah=Koneksidb.createStatement();
                perintah.execute(sqlsimpan);
                JOptionPane.showMessageDialog(null, "Data ID Server berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void simpanIDServer02(String Nama, String Alamat){
        try {
            String sqlsimpan="INSERT INTO ID_Server (Nama, Alamat) VALUES (?, ?)";
            String sqlcari= "SELECT*FROM ID_Server WHERE Nama = ?";
            
            PreparedStatement cari = Koneksidb.prepareStatement(sqlcari);
            cari.setString(1, Nama);
            ResultSet data = cari.executeQuery();
            
            if (data.next()){
                JOptionPane.showMessageDialog(null, "Nama ID Server sudah terdaftar");
                this.duplikasi = true;
                this.CEK_ALAMAT_ID = data.getString("Alamat");
            } else {
                this.duplikasi = false;
                this.CEK_ALAMAT_ID = null;
                
                PreparedStatement perintah = Koneksidb.prepareStatement(sqlsimpan);
                perintah.setString(1, Nama);
                perintah.setString(2, Alamat);
                perintah.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data ID Server berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void ubahIDServer(String Nama, String Alamat){
        try {
            String sqlubah="UPDATE ID_Server SET Alamat = ? WHERE Nama = ?";
            PreparedStatement perintah = Koneksidb.prepareStatement(sqlubah);
            perintah.setString(1, Alamat);
            perintah.setString(2, Nama); // Nama sebagai parameter terakhir (PK)
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data ID Server berhasil diubah");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void hapusIDServer(String Nama){
        try {
            String sqlhapus="DELETE FROM ID_Server WHERE Nama = ?";
            PreparedStatement perintah = Koneksidb.prepareStatement(sqlhapus);
            perintah.setString(1, Nama);
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data ID Server berhasil dihapus");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void tampilDataIDServer(JTable komponentabel, String SQL){
        try {
            PreparedStatement perintah = Koneksidb.prepareStatement(SQL);
            ResultSet data = perintah.executeQuery();
            ResultSetMetaData meta = data.getMetaData();
            int jumlahkolom = meta.getColumnCount();
            DefaultTableModel modeltabel = new DefaultTableModel();
            
            modeltabel.addColumn("Nama");
            modeltabel.addColumn("Alamat");
            
            while(data.next()){
                Object[] row = new Object[jumlahkolom];
                for(int i=1; i<=jumlahkolom; i++){
                    row[i-1]=data.getObject(i);
                }
                modeltabel.addRow(row);
            }
            komponentabel.setModel(modeltabel);
        } catch (Exception e) {
        }
    } 
} 
    
