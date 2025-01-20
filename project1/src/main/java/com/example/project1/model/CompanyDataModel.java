package com.example.project1.model;

import com.example.project1.observers.Observer;
import com.example.project1.observers.Subject;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "historical_data")
@Data
@NoArgsConstructor
public class CompanyDataModel implements Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "last_transaction_price")
    private Double lastTransactionPrice;

    @Column(name = "max_price")
    private Double maxPrice;

    @Column(name = "min_price")
    private Double minPrice;

    @Column(name = "average_price")
    private Double averagePrice;

    @Column(name = "percentage_change")
    private Double percentageChange;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "turnorver_best")
    private Integer turnoverBest;

    @Column(name = "total_turnover")
    private Integer totalTurnover;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private CompanyIssuerModel company;

    public CompanyDataModel(LocalDate date, Double lastTransactionPrice, Double maxPrice, Double minPrice, Double averagePrice, Double percentageChange, Integer quantity, Integer turnoverBest, Integer totalTurnover) {
        this.date = date;
        this.lastTransactionPrice = lastTransactionPrice;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.averagePrice = averagePrice;
        this.percentageChange = percentageChange;
        this.quantity = quantity;
        this.turnoverBest = turnoverBest;
        this.totalTurnover = totalTurnover;
    }

    @Transient
    private List<Observer> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }

    public void setLastTransactionPrice(Double lastTransactionPrice) {
        this.lastTransactionPrice = lastTransactionPrice;
        notifyObservers();
    }


}
