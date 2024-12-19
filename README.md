# Minim2_DSA

# Abast de la Implementació

### Backend
#### Implementat
- Ruta `GET /user/{id}/badges/`:
  - Simula la resposta amb dades fictícies (dummy).
  - Mostra la resposta en format JSON segons el requeriment de l'exercici.
- Lògica bàsica per gestionar peticions i generar respostes estàtiques.

#### Funciona
- L'API respon correctament a les peticions realitzades des de l'aplicació Android.

### Android
#### Implementat
- Nova secció en el perfil per visualitzar les insígnies:
  - Disseny de la interfície.
  - Lògica per realitzar la crida a l'API utilitzant Retrofit.
  - Renderització del llistat d'insígnies basat en la resposta de l'API.

#### Funciona
- La interfície mostra correctament el llistat d'insígnies en base a la resposta del backend.
- La connexió amb l'API es realitza sense problemes.

## Notes Addicionals
- El projecte segueix les indicacions de l'enunciat, utilitzant dades fictícies i evitant dependències amb bases de dades.
- La implementació compleix amb l' ús obligatori de Retrofit per a les crides a l'API.
- El repositori està dividit en Android i Backend, s'han comprovat que els endpoints GET de les insignies per id d'usuari funcionen amb el Swagger.

### Informació Extra
- Com no es requeria POST, s'han utilitzat els usuaris predeterminats "Alice" amb pass: "123" i "Bob" amb pass: "456" per a realitzar les comprovacions.
- S'ha assignat un id a Alice i un a Bob per a que almenys ells tinguessin dues insignies cadascun; s'ha d'iniciar sessió amb ells.
- S'ha creat un botó INSIGNIAS dins el submenú CONFIGURACIÓ PERFIL on es poden consultar les insignies de l'usuari. Si no en tenen, respon amb un Toast.
- El mode de visualització de les insignies és l'esperat amb l'avatar i el nom corresponent. S'han afegit un parell d'insignies més tal com recomanava l'enunciat, amb punts suspensius per a demostrar el total funcionament del projecte.

