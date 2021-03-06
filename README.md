# UniPlanner
Applicazione Android che rende semplice e veloce la gestione della propria carriera universitaria.
Compatibile con la maggior parte dei dispositivi Android (API 17 e successive).
Nelle sezioni successive sono descritte le principali funzionalità.

## Registrazione
Al primo avvio dell’app viene richiesto l’inserimento delle informazioni principali necessarie al funzionamento.
In seguito, tali informazioni saranno modificabili tramite le impostazioni. Se viene premuto il tasto “Inizia” senza aver riempito tutti i campi viene mostrato un Toast contenente il messaggio: “Riempi tutti i campi”.\
Una volta completato il procedimento l’utente si ritroverà nella sua dashboard, ovvero la Home.

<p align="center"> 
<img src="screenshots/registration.png" width="200" height="350">
</p>

## Homepage
La schermata Home svolge la funzione di dashboard. Nella parte superiore sono riportati l’università e il corso di studi inseriti dallo studente.\
Nella parte inferiore vengono visualizzate tutte le informazioni più importanti: la media, il numero di esami superati, la proiezione del voto di laurea, i crediti acquisiti e l’appello più ravvicinato.

<p align="center"> 
<img src="screenshots/home.png" width="200" height="350">
</p>

## Menu
Il menu permette di cambiare rapidamente schermata ovunque l’utente si trovi.\
Le voci presenti sono quelle già anticipate.\
Cliccando sull’immagine l’utente può cambiare la propria immagine del profilo caricandone una dalla propria galleria.\
Per fare ciò la prima volta l’applicazione richiederà il permesso per poter leggere la memoria del dispositivo.

<p align="center"> 
<img src="screenshots/menu.png" width="200" height="350">
</p>


## Le mie Materie
In questa sezione vengono mostrati tutti i corsi seguiti dallo studente. 
Ogni voce contiene il nome del corso, il professore e il colore scelto per identificare rapidamente la lezione di quel corso all’interno dell’orario.\
Cliccando sul ‘+’ in alto a destra è possibile aggiungere un nuovo insegnamento.\
Per aggiungere una nuova materia vengono richiesti il nome della materia, il professore, i CFU e il colore.
Cliccando su “Scegli un colore” verrà aperto un Color Picker che permette allo studente di avere un’ampia scelta di colori.\
Una volta cliccato su “Ok” il colore scelto apparirà nel riquadro a fianco al bottone.\
Facendo uno “swipe” verso sinistra sopra un elemento presente nella lista compariranno due pulsanti che ne permettono la modifica o l’eliminazione.\
Eliminando una materia verranno automaticamente eliminati tutti gli esami e le lezioni ad essa collegati.

<p align="center"> 
<img src="screenshots/subjects.png" width="200" height="350">
<img src="screenshots/add_subject.png" width="200" height="350">
<img src="screenshots/color_picker.png" width="200" height="350">
</p>

## Orario settimanale
L’orario permette di visualizzare le materie della settimana suddivise per giorni, semplicemente cliccando sul giorno desiderato.
Posizionandosi su un giorno e cliccando sul ‘+’ si potrà aggiungere una nuova lezione al giorno stesso. Ogni voce dell’orario contiene la materia, l’aula in cui si svolge la lezione, l’ora di inizio, l’ora di fine, il professore e il colore associato a quella materia.\
L’aggiunta di una lezione richiede la selezione di una materia tra quelle già inserite, l’aula in cui si svolgerà la lezione, l’ora di inizio e l’ora di fine.
Cliccando all’interno dei campi per inserire l’ora si aprirà una finestra con un Time Picker, che permetterà di selezionare facilmente le ore e i minuti in due modalità.\
Se viene inserita un’ora di fine lezione che precede quella di inizio viene mostrato un messaggio di errore.\
Una volta che tutti i campi sono stati completati correttamente la lezione verrà aggiunta all’orario e verrà mostrato un messaggio di avvenuta aggiunta.

<p align="center"> 
<img src="screenshots/week_schedule.png" width="200" height="350">
<img src="screenshots/add_lesson.png" width="200" height="350">
<img src="screenshots/time_picker.png" width="200" height="350">
<img src="screenshots/time_picker_digital.png" width="200" height="350"> 
</p>

## Libretto
Il libretto raccoglie tutti gli esami superati. Ogni voce mostra il nome del corso, la data in cui è stato sostenuto l’esame, il voto e i CFU acquisiti.\
Anche in questo caso gli esami sono ordinati a partire dal più recente. Analogamente alla schermata degli esami futuri è possibile aggiungere un esame al libretto cliccando sul ‘+’ in alto a destra, selezionando la materia e inserendo la data e il voto.\
L’aggiunta di un esame al libretto funziona in modo analogo all’aggiunta di un esame futuro.\
I campi richiesti sono la materia, il voto conseguito e la data in cui è stato sostenuto l’esame. La selezione della data avviene sempre tramite l’apertura di un Date Picker, come già mostrato nelle slide precedenti.\
Nessun campo può essere lasciato vuoto e il voto inserito deve essere compreso tra 18 e 30.

<p align="center"> 
<img src="screenshots/booklet.png" width="200" height="350">
<img src="screenshots/add_passed_exam.png" width="200" height="350">
</p>

## Esami Futuri
In questa schermata è possibile visualizzare la lista dei prossimi appelli che lo studente ha intenzione di sostenere, ordinati a partire dal più vicino. Per ogni voce vengono mostrati il nome del corso, la data e i CFU.\
L’esame più ravvicinato verrà mostrato anche nella sezione Home.\
Cliccando sul ‘+’ in alto a destra è possibile aggiungere un nuovo esame.
Per aggiungere un esame è necessario selezionare una materia tra quelle presenti nella propria lista e inserire la data in cui si terrà l’appello.\
Lo spinner mostra l’elenco dei corsi inseriti dallo studente.\
Cliccando sul campo “Data dell’esame” si aprirà una finestra con un Date Picker che permette di selezionare facilmente la data dell’esame.
Ancora una volta è possibile modificare o eliminare le voci presenti nella lista facendo uno swipe verso sinistra. Questo permetterà di modificare o eliminare l’elemento.\
Una volta cliccato “Elimina” l’esame verrà rimosso dalla lista e viene confermata l’eliminazione tramite un messaggio.

<p align="center"> 
<img src="screenshots/future_exams.png" width="200" height="350">
<img src="screenshots/add_future_exam.png" width="200" height="350">
<img src="screenshots/date_picker.png" width="200" height="350">
</p>

## Notifiche
Il servizio di notifica fa sì che l’app ricordi allo studente tra quanti giorni si svolgerà l’esame più vicino.
Verrà inviata una notifica 7 giorni prima, 3 giorni prima e il giorno prima (alle 10 di mattina).

<p align="center"> 
<img src="screenshots/notification.png" width="200" height="350">
</p>

## Impostazioni
Tramite la schermata delle impostazioni è possibile modificare l’Università, il corso di studi, i crediti da acquisire e il numero di matricola.\
Per fare ciò è sufficiente cliccare sulla voce che si vuole modificare e comparirà un campo di testo.\
Inoltre è possibile scegliere il tipo di media da visualizzare: aritmetica o ponderata.\
Infine è possibile eliminare tutti i dati personali. In questo modo lo studente verrà riportato alla schermata di registrazione.

<p align="center"> 
<img src="screenshots/settings.png" width="200" height="350">
<img src="screenshots/set_course.png" width="200" height="350">
<img src="screenshots/choose_avg_type.png" width="200" height="350">
<img src="screenshots/delete_data.png" width="200" height="350">
</p>


