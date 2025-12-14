package org.project;

import java.util.Random;

public enum RandomProductName {
    Хлеб,
    Молоко,
    Яблоко,
    Сыр,
    Колбаса,
    Сок,
    Рис,
    Мука,
    Яйцо,
    Чай,
    Кофе,
    Йогурт,
    Масло,
    Шоколад,
    Помидор,
    Огурец,
    Картофель,
    Лук,
    Сахар,
    Соль,
    Кефир,
    Слива,
    Груша,
    Перец,
    Рыба,
    Горох,
    Кукуруза,
    Клубника,
    Лимонад,
    Шпроты;


    private static final Random random = new Random();
    public static String getRandomProduct(){
        RandomProductName [] values = RandomProductName.values();
        return values[random.nextInt(values.length)].name();
    }
}
