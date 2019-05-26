package app;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static app.db_driver.*;


public class MainWindowController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadBooks();

        LV.setItems(FXCollections.observableArrayList(getBooks_names(getAll_books())));
        current_index = -1;
        LocalBooksList.addAll(getAll_books());
        if (!AdminMode) {
            System.out.println("1");
            AdminToolbar.setVisible(false);
            AdminEditButton.setVisible(false);
            AdminSaveButton.setVisible(false);
        } else {
            System.out.println("2");
            AdminToolbar.setDisable(false);
            // Edit and Save are opposite
            AdminEditButton.setDisable(false);
            AdminSaveButton.setDisable(true);
        }
    }

    private int current_index;
    private List<Book> LocalBooksList = new ArrayList<>();

    @FXML
    ListView<String> LV;
    @FXML
    ToolBar AdminToolbar;
    @FXML
    Button AdminPlusButton, AdminEditButton, AdminSaveButton;
    @FXML
    TextField BookNameField, AuthorField, PriceField, ISBNField, DateField, DescriptionField;
    @FXML
    TextField BookNameSearchField, ISBNSearchField, AuthorNameSearchField, PriceSearchField;

    @FXML
    public void chooseItemInList() {
        current_index = LV.getSelectionModel().getSelectedIndex();
        previewBook(LocalBooksList.get(current_index));
        System.out.println(current_index);
    }

    @FXML
    public void Search() {
        System.out.println("pressed");
        //ISBN is unique so in goes first
        if (!ISBNSearchField.getText().isEmpty()) {
            searchByISBN(ISBNSearchField.getText());
        } else {
            if (!BookNameSearchField.getText().isEmpty())
                searchByName(BookNameSearchField.getText());
            else if (!AuthorNameSearchField.getText().isEmpty())
                searchByAuthor(AuthorNameSearchField.getText());
            else if (!PriceSearchField.getText().isEmpty())
                searchByPrice(Double.parseDouble(PriceSearchField.getText()));
            else {
                LocalBooksList.clear();
                LocalBooksList.addAll(getAll_books());
            }
        }
        updateListView(getBooks_names(LocalBooksList));
    }

    @FXML
    public void EditBook() {

    }

    @FXML
    public void SaveBook() {

    }

    @FXML
    public void AddNewBook() {

    }


    private void updateListView(ArrayList<String> books_names_list) {
        LV.getItems().clear();
        LV.setItems(FXCollections.observableArrayList(books_names_list));
    }

    private void previewBook(Book book) {
        BookNameField.setText(book.getName());
        AuthorField.setText(book.getAuthor());
//        PriceField.setText(Double.toString(book.getPrice()));
        ISBNField.setText(book.getISBN());
        DateField.setText(book.getDate().toString());
        DescriptionField.setText(book.getDescription());
    }

    private void clearBookPreview() {
        BookNameField.clear();
        AuthorField.clear();
        PriceField.clear();
        ISBNField.clear();
        DescriptionField.clear();
    }

    private void searchByISBN(String ISBN) {
        List<Book> temp = LocalBooksList.stream()
                .filter(p -> p.getISBN().equals(ISBN))
                .collect(Collectors.toList());
        int i = 0;
        for (Book x : temp) {
            //mb <
            if (i < LocalBooksList.size())
                LocalBooksList.set(i++, x);
        }
        for (int x = LocalBooksList.size() - 1; x >= i; x--)
            LocalBooksList.remove(x);

    }

    private void searchByName(String book_name) {
        System.out.println("By name");
        // calls on itself so more filters for search can be applied
        int temp_size = LocalBooksList.size() - 1;

        LocalBooksList.addAll(
                LocalBooksList
                        .stream()
                        .filter(p -> p.getName().contains((book_name)))
                        .collect(Collectors.toList()));
        // adds books at the end of AL, so everything if front can be deleted

        for (; temp_size >= 0; temp_size--)
            LocalBooksList.remove(temp_size);
    }

    private void searchByAuthor(String author_name) {
        int temp_size = LocalBooksList.size() - 1;

        LocalBooksList.addAll(
                LocalBooksList
                        .stream()
                        .filter(p -> p.getAuthor().contains((author_name)))
                        .collect(Collectors.toList()));

        for (; temp_size >= 0; temp_size--)
            LocalBooksList.remove(temp_size);
    }

    private void searchByPrice(Double priceUp) {
        int temp_size = LocalBooksList.size() - 1;
        LocalBooksList.addAll(
                LocalBooksList
                        .stream()
                        .filter(p -> p.getPrice() <= priceUp)
                        .collect(Collectors.toList()));

        for (; temp_size >= 0; temp_size--)
            LocalBooksList.remove(temp_size);
    }

    //*******************************
    //      Serving Functions       *
    //*******************************
    static ArrayList<String> getBooks_names(List<Book> all_books) {

        ArrayList<String> listofnames = new ArrayList<>();
        for (Book x : all_books)
            listofnames.add(x.getName());

        return listofnames;
    }
}
