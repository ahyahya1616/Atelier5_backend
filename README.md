# Rapport d'Analyse du Dépôt Backend : Gestion de Stations-Service

Ce document présente une analyse détaillée du code backend fourni pour l'atelier de gestion des stations-service. Le projet est développé en utilisant l'écosystème Jakarta EE, en se concentrant sur une architecture RESTful pour communiquer avec un client frontend.

## 1. Architecture et Technologies Utilisées

Le backend est construit sur une pile technologique Jakarta EE moderne, exploitant plusieurs spécifications clés pour assurer la robustesse, la persistance des données et la communication web.

*   **JAX-RS (Jakarta RESTful Web Services)** : Utilisé pour créer les points d'accès (endpoints) de l'API REST. La classe `RestApplicationStation` configure le chemin de base de l'API à `/api`.
*   **JPA (Jakarta Persistence API)** : Employé pour la gestion de la persistance des données et le mappage objet-relationnel (ORM). L'implémentation utilisée est **Hibernate**.
*   **EJB (Enterprise JavaBeans)** / **CDI (Contexts and Dependency Injection)** : Bien que non visibles dans les extraits fournis, ces technologies sont fondamentales dans une telle architecture pour gérer la logique métier (services) et l'injection de dépendances.
*   **Lombok** : Une bibliothèque utilisée pour réduire le code répétitif (boilerplate) dans les classes Java, notamment pour les getters, setters, et constructeurs via des annotations (`@Getter`, `@Setter`, `@AllArgsConstructor`, etc.).
*   **Serveur d'application** : La configuration `persistence.xml` (avec `transaction-type="JTA"`) suggère que l'application est déployée sur un serveur d'application compatible Jakarta EE comme **WildFly**.

## 2. Modèle de Données (Entités JPA)

La base de données est structurée autour de trois entités principales qui modélisent le domaine de l'application.

### Entités

1.  **`Station`**: Représente une station-service.
    *   `id`: Identifiant unique.
    *   `nom`: Nom de la station.
    *   `ville`: Ville où se trouve la station.
    *   `adresse`: Adresse complète.

2.  **`Carburant`**: Représente un type de carburant (ex: Diesel, Sans Plomb 95).
    *   `id`: Identifiant unique.
    *   `nom`: Nom du carburant.
    *   `description`: Description détaillée.

3.  **`HistoCarb`**: Représente l'historique des prix d'un carburant dans une station donnée à une date spécifique. C'est une table de liaison avec des données additionnelles.
    *   `id`: Identifiant unique.
    *   `date`: Date de l'enregistrement du prix.
    *   `prix`: Le prix du carburant à cette date.

### Relations

Les entités sont liées par les relations suivantes :

*   **`Station` <-> `HistoCarb`**: Une relation `One-to-Many`. Une station peut avoir plusieurs enregistrements d'historique de prix.
    ```java
    // Dans l'entité Station
    @OneToMany(mappedBy = "station", cascade = CascadeType.ALL)
    private List<HistoCarb> histoCarburants;
    ```

*   **`Carburant` <-> `HistoCarb`**: Une relation `One-to-Many`. Un type de carburant peut être présent dans plusieurs enregistrements d'historique.
    ```java
    // Dans l'entité Carburant
    @OneToMany(mappedBy = "carburant", cascade = CascadeType.ALL)
    private List<HistoCarb> histoCarburants;
    ```

*   **`HistoCarb` -> `Station` / `Carburant`**: Des relations `Many-to-One` inverses, liant un enregistrement d'historique à une station unique et à un carburant unique.
    ```java
    // Dans l'entité HistoCarb
    @ManyToOne
    @JoinColumn(name = "station_id")
    private Station station;

    @ManyToOne
    @JoinColumn(name = "carburant_id")
    private Carburant carburant;
    ```

## 3. Couche d'Accès aux Données (JPA / Hibernate)

La configuration de la persistance est définie dans le fichier `src/main/resources/META-INF/persistence.xml`.

```xml
<persistence-unit name="pu" transaction-type="JTA">
    <jta-data-source>java:/jdbc/StationDS</jta-data-source>

    <!-- Entités du projet -->
    <class>ma.fstt.atelier5_backend.entities.Station</class>
    <class>ma.fstt.atelier5_backend.entities.Carburant</class>
    <class>ma.fstt.atelier5_backend.entities.HistoCarb</class>

    <properties>
        <property name="hibernate.hbm2ddl.auto" value="update"/>
        <property name="hibernate.show_sql" value="true"/>
        <!-- ... autres propriétés -->
    </properties>
</persistence-unit>

```

*   **`Unité de persistance (pu)`**: Définit le contexte de persistance.
*   **`Source de données JTA (java:/jdbc/StationDS)`**: Indique que la gestion des transactions est déléguée au serveur d'application (container-managed), ce qui est une bonne pratique.
*   **`hibernate.hbm2ddl.auto="update"`**: Configure Hibernate pour mettre à jour automatiquement le schéma de la base de données au démarrage de l'application en fonction des entités. C'est pratique en développement, mais doit être utilisé avec prudence en production.

## 4. API REST et DTOs

Pour découpler l'API externe du modèle de données interne, des **Data Transfer Objects (DTOs)** sont utilisés. Cela permet de ne pas exposer directement les entités JPA et de personnaliser les données envoyées au client.

*   `StationDTO`
*   `CarburantDTO`
*   `HistoCarbDTO`

Ces DTOs sont de simples classes POJO (Plain Old Java Objects) avec des champs correspondant aux données à transférer.

### 5. Configuration CORS

La communication entre un frontend (ex: `localhost:4200`) et un backend (`localhost:8080`) est soumise à la politique de même origine (Same-Origin Policy) des navigateurs. Le fichier `CORSFilter.java` est mis en place pour autoriser les requêtes cross-origin.

```java
@Provider
public class CORSFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext,
                       ContainerResponseContext responseContext) throws IOException {

        responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
        responseContext.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
        responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        // ...
    }
}
```
Cette configuration est très permissive (`"Access-Control-Allow-Origin", "*"`), ce qui est acceptable pour le développement. En production, il serait plus sécurisé de restreindre l'origine à l'URL du frontend déployé.

## 6. Conclusion et Axes d'Amélioration

**Points Forts :**
*   Architecture solide basée sur les standards Jakarta EE.
*   Bonne pratique de découplage avec l'utilisation de DTOs.
*   Utilisation de Lombok pour un code plus concis et lisible.
*   Gestion correcte de la persistance via JPA et une configuration JTA.
