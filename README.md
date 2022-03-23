# KBE_Abgabebeleg_WiSe21_22
Abgabebeleg für Komponentenbasierte Entwicklung<br>
Autoren:
* Marco Heger s0563424
* Nicklas Hanzig s0563654


##Wichtige Voraussetzung dieser Anwendung:

###Als Umgebungsvariabeln müssen diese Angaben für Datenbanken vorhanden sein:

Für Main_Component_Microservice:
* kbe_postgreSQL_abgabebeleg_mainComp_path
* kbe_postgreSQL_abgabebeleg_mainComp_benutzer
* kbe_postgreSQL_abgabebeleg_mainComp_password

Für Data_Warehouse_Microservice:
* kbe_postgreSQL_abgabebeleg_dataWarehouse_path
* kbe_postgreSQL_abgabebeleg_dataWarehouse_benutzer
* kbe_postgreSQL_abgabebeleg_dataWarehouse_password


##Komponentenaufgaben
Vorhandene Komponenten ergeben insgesamt eine Anwendung. Dazu ist jeder einzelne Dienst eine REST-Applikation als Miccroservice.
* react_Gui_Microservice - Vereinfachte Ansicht fuer den Benutzer
* Gateway_Microservice - Weiterleitung von Nachrichten zu den anderen Services
* MwSt_Microservice - Berechnung von Steuern
* Main_Component_Microservice - Verarbeitung alle Informationen einer Anfrage. Kommunikationen auch zu den anderen Services und zu externen Service
* Data_Warehouse_Microservice - Speicherung sämtlicher Daten