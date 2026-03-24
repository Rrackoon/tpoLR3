# Лабораторная работа №3

Вариант №6211: `WolframAlpha` - [wolframalpha.com](https://www.wolframalpha.com/)

## Что есть в проекте

- 15 расширенных `Use Case`;
- 23 тест-кейса покрытия;
- автоматические UI-тесты на `Selenium`;
- page objects и Selenium-инфраструктура в `src/main/java/ru/itmo`;
- тестовый слой в `src/test/java/ru/itmo`.

## Структура

- `src/main/java/ru/itmo/browser` - выбор браузера и создание драйвера;
- `src/main/java/ru/itmo/pages` - page objects;
- `src/test/java/ru/itmo` - JUnit-тесты, разбитые по функциональным классам;
- `docs/use-cases.md` - прецеденты использования;
- `docs/test-coverage.md` - покрытие;
- `docs/report.md` - краткий отчет.

## Как запускать

Chrome:

```bash
mvn test -Dbrowser=chrome
```

Firefox:

```bash
mvn test -Dbrowser=firefox
```

С видимым окном браузера:

```bash
mvn test -Dbrowser=chrome -Dheadless=false
```

## Важная особенность WolframAlpha

Результаты WolframAlpha часто рендерятся не обычным текстом, а изображениями с осмысленным `alt`. Поэтому в тестах используются:

- `XPath`-локаторы;
- заголовки pod-блоков;
- `alt` изображений;
- наличие визуальных элементов `img`, `svg`, `canvas`;
- проверка URL и заголовка страницы.

## Замечание по Selenium RC

Хотя в формулировке задания указан `Selenium RC`, для современных браузеров рабочим и актуальным решением является `Selenium WebDriver`. Именно он и используется в проекте.
