package com.example.oblig4.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.oblig4.entities.Address;
import com.example.oblig4.entities.Album;
import com.example.oblig4.entities.Company;
import com.example.oblig4.entities.Geo;
import com.example.oblig4.entities.User;
import com.example.oblig4.entities.UserWithAlbums;
import com.example.oblig4.entities.UserWithCompanyWithAddressWithGeo;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertUser(User user);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertCompany(Company company);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertAddress(Address address);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertGeo(Geo geo);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertAlbum(Album album);

    @Transaction
    @Query("DELETE FROM user")
    void deleteAll();

    @Transaction
    @Query("DELETE FROM User WHERE userId = :userId")
    void delete(long userId);


    @Transaction
    @Query("DELETE FROM Album WHERE albumId = :albumId")
    void deleteAlbum(long albumId);

    @Query("SELECT * FROM User")
    LiveData<List<User>> getAll();

    @Transaction
    @Query("SELECT * FROM User")
    LiveData<List<UserWithAlbums>> getAllUsersWithAlbums();

    @Transaction
    @Query("SELECT * FROM User WHERE User.userId = :userId")
    LiveData<UserWithAlbums> getUserWithAlbums(long userId);

    @Transaction
    @Query("SELECT * FROM User JOIN Company ON User.fk_companyId = companyId JOIN Address ON User.fk_addressId = Address.addressId JOIN Geo ON Address.fk_geoId = Geo.geoId where User.userId = :userId")
    LiveData<UserWithCompanyWithAddressWithGeo> getUserWithCompanyWithAddressWithGeo(long userId);

    @Transaction
    @Query("SELECT * FROM User JOIN Company ON User.fk_companyId = companyId JOIN Address ON User.fk_addressId = Address.addressId JOIN Geo ON Address.fk_geoId = Geo.geoId")
    LiveData<List<UserWithCompanyWithAddressWithGeo>> getAllUsersWithCompanyWithAddressWithGeo();

}
