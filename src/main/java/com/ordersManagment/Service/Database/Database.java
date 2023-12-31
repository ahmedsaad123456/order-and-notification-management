package com.ordersManagment.Service.Database;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Database {

    private DatabaseConnection connection;

    public void connect(){
        connection.connect();
    }

    public void disconnect(){
        connection.disconnect();
    }

}