graph TD
    %% 1-ші ТАПСЫРМА: ДИЯГРАММА ДЕЯТЕЛЬНОСТИ (ЖҰМЫСҚА АЛУ ПРОЦЕСІ)
    subgraph "Задание №1: Управление наймом (Activity Diagram)"
        Start1([Начало]) --> CreateReq[Руководитель создает заявку] [cite: 61]
        CreateReq --> HRCheck{Заявка соответствует?} 
        
        HRCheck -- Нет --> NotifyManager[Уведомление руководителя о доработке] [cite: 64]
        NotifyManager --> CreateReq
        HRCheck -- Да --> Approve[Утверждение заявки] [cite: 63]
        
        Approve --> Publish[Публикация вакансии на сайте] [cite: 66]
        
        %% Параллельные действия
        state fork_node <<fork>>
        Publish --> fork_node
        fork_node --> Apply[Кандидаты подают заявки] [cite: 67]
        fork_node --> Collect[HR собирает анкеты] 
        
        state join_node <<join>>
        Apply --> join_node
        Collect --> join_node
        
        join_node --> Review[HR проверяет анкеты] 
        
        state CandidateFit <<choice>>
        Review --> CandidateFit
        CandidateFit -- Нет --> Reject[Отклонение анкеты] [cite: 69]
        CandidateFit -- Да --> Interview[Приглашение на собеседование] [cite: 70]
        
        Interview --> HR_Int[Первичное интервью HR] [cite: 72]
        HR_Int --> Tech_Int[Техническое собеседование с руководителем] [cite: 73]
        
        state IntResult <<choice>>
        Tech_Int --> IntResult
        IntResult -- Провал --> NotifyFail[Уведомление об отказе] [cite: 75]
        IntResult -- Успех --> Offer[Предложение оффера] [cite: 74]
        
        Offer --> Accept[Кандидат подтверждает оффер] [cite: 77]
        Accept --> AddToDB[Система добавляет сотрудника в БД] [cite: 78]
        AddToDB --> ITConfig[IT-отдел настроивает рабочее место] [cite: 79]
        ITConfig --> End1([Конец процесса]) [cite: 87]
    end

    %% 2-ші ТАПСЫРМА: ДИЯГРАММА ПОСЛЕДОВАТЕЛЬНОСТИ (БРОНЬДАУ)
    %% Бұл жерде визуализация үшін логикалық блок көрсетілген
    subgraph "Задание №2: Бронирование мероприятий (Sequence Logic)"
        Client((Клиент)) -- "Запрос площадки" --> System[Система] [cite: 104]
        System -- "Проверка дат" --> System [cite: 105]
        
        System -- "Если доступно: Стоимость" --> Client [cite: 106]
        Client -- "Подтверждение" --> System [cite: 109]
        
        System -- "Запрос оплаты" --> Gateway[Платежный шлюз] [cite: 110]
        Gateway -- "Успех/Отказ" --> System [cite: 111, 112]
        
        System -- "Уведомление" --> Admin[Администратор] [cite: 111]
        
        %% Параллельные задачи подрядчикам
        Admin -- "Задачи" --> Contractors{Подрядчики} [cite: 115]
        Contractors -- "Подтверждение" --> System [cite: 116]
        
        System -- "Сбор отзывов" --> Manager[Менеджер] [cite: 120]
    end
