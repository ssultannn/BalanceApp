package az.developia.BalanceApp.service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import az.developia.BalanceApp.entity.IncomeEntity;
import az.developia.BalanceApp.repository.IncomeRepository;
import az.developia.BalanceApp.response.IncomeResponse;

@Service
public class IncomeService {

    @Autowired
    private IncomeRepository incomeRepository;

    public IncomeEntity addIncome(IncomeEntity income) {
        return incomeRepository.save(income);
    }

    public List<IncomeEntity> getIncomesByUser(Long userId) {
        return incomeRepository.findByUserId(userId);
    }
    public List<IncomeEntity> getSortedIncomesByUser(Long userId, String sortBy) {
        List<IncomeEntity> incomes = incomeRepository.findByUserId(userId);

        if (sortBy == null || sortBy.isEmpty()) {
            sortBy = "date"; // Значение по умолчанию
        }

        if ("date".equalsIgnoreCase(sortBy)) {
            incomes.sort(Comparator.comparing(IncomeEntity::getDate));
        } else if ("amount".equalsIgnoreCase(sortBy)) {
            incomes.sort(Comparator.comparing(IncomeEntity::getAmount));
        }

        return incomes;
    }
    public List<IncomeEntity> getFilteredIncomesByUser(Long userId, String sortBy, LocalDate startDate, LocalDate endDate) {
        List<IncomeEntity> incomes = incomeRepository.findByUserId(userId);

        // Фильтрация по датам, если они указаны
        if (startDate != null) {
            incomes = incomes.stream()
                    .filter(income -> !income.getDate().isBefore(startDate))
                    .collect(Collectors.toList());
        }

        if (endDate != null) {
            incomes = incomes.stream()
                    .filter(income -> !income.getDate().isAfter(endDate))
                    .collect(Collectors.toList());
        }

        // Сортировка по выбранному полю
        if ("date".equalsIgnoreCase(sortBy)) {
            incomes.sort(Comparator.comparing(IncomeEntity::getDate));
        } else if ("amount".equalsIgnoreCase(sortBy)) {
            incomes.sort(Comparator.comparing(IncomeEntity::getAmount));
        }

        return incomes;
    }

}