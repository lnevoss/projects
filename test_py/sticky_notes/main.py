import sys
from uuid import uuid4

from PySide6.QtWidgets import *
from PySide6.QtCore import *

from custom_layout import FlowLayout

class Note:
    def __init__(self, title, text):
        self.uuid = str(uuid4())
        self.title = title
        self.text = text

class NoteTile(QWidget):
    clicked = Signal()

    def __init__(self, note):
        super().__init__()
        self.note = note
        self.label = QLabel(note.title)
        QVBoxLayout(self).addWidget(self.label)

    def mousePressEvent(self, event):
        self.clicked.emit()

class NoteWindow(QMainWindow):
    title_changed = Signal(str,str)
    text_changed = Signal(str,str)

    def __init__(self, note):
        super().__init__()

        self.title = QTextEdit(note.title)
        self.text = QTextEdit(note.text)
        self.container = QWidget()
        layout = QVBoxLayout(self.container)
        layout.addWidget(self.title)
        layout.addWidget(self.text)
        
        self.setCentralWidget(self.container)


        self.title.textChanged.connect(
            lambda: self.title_changed.emit(note.uuid, self.title.toPlainText())
        )

        self.text.textChanged.connect(
            lambda: self.text_changed.emit(note.uuid, self.text.toPlainText())
        )

        # self.title.textChanged.connect(self.title_changed.emit)
        # self.text.textChanged.connect(self.text_changed.emit)



note_style =    """
                background-color: #2a2a2a;
                border: 1px solid #3a3a3a;
                border-radius: 12px;
                """

 

generic_add_note_tile = Note("+", "")
generic_placeholder_tile = Note("type your title here", "type your text here")

with open("style.qss", "r") as f:
    _style = f.read()


class MainWindow(QMainWindow):
    title_change = Signal(str)

    notes = [
        Note("hello", "text inside"),
        Note("hello", "text still inside"),
    ]  

    open_note_windows = [

    ]

    tiles = {

    }

    def __init__(self):
        super().__init__()

        self.core = QWidget()
        self.core_layout = FlowLayout(self.core)
    
        self.setup_note_tiles()

        self.resize(850, 650)

        self.setCentralWidget(self.core)
        
    def setup_note_tiles(self):
        for note in self.notes:
            self.create_note_tile(note, lambda n=note: self.open_note(n))

        #move add note from the tile and make the tile call a method that will process the add request
        self.create_note_tile(generic_add_note_tile, lambda: self.add_note(generic_placeholder_tile)) 

    def create_note_tile(self, note, onclick):
        note_tile = NoteTile(note)
        note_tile.setObjectName("note")
        note_tile.clicked.connect(onclick)
        note_tile.setStyleSheet(note_style)
        note_tile.setFixedSize(200,300)
        self.core_layout.addWidget(note_tile)
        self.tiles[note.uuid] = note_tile

    def add_note(self, note):
        self.delete_note(self.tiles.popitem()[1])
        self.create_note_tile(note, lambda: self.open_note(note))
        self.create_note_tile(generic_add_note_tile, lambda: self.add_note(generic_placeholder_tile))

    #rework later to select and delete specific tiles
    def delete_note(self, tile):
        print(tile)
        tile.deleteLater()

    def open_note(self, note):
        note_window = NoteWindow(note)
        note_window.resize(300,400)
        self.open_note_windows.append(note_window)

        note_window.title_changed.connect(self.change_note)

        note_window.show()

    def change_note(self, uuid, text):
        print("triggered change note")
        print(uuid, text)
        tile_to_change = self.tiles.get(uuid)
        tile_to_change.label.setText(text)


        #rework for simplicity
        for n in self.notes:
            if n.uuid == uuid:
                n.title = text
                break
        return


app = QApplication(sys.argv)
app.setStyleSheet(_style)

window = MainWindow()
window.show()

app.exec()

