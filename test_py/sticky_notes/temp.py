import sys
from uuid import uuid4

# good luck future me 
# pass into the tile self and extract a signal. figure out how you do it or choose something else

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

    #type
    #0 = normal
    #1 = add

    #0 = lambda n=note: self.open_note(n)
    #1 = lambda: self.add_note(generic_placeholder_tile)

    def __init__(self, note, type):
        super().__init__()
        self.note = note
        self.type = type
        self.label = QLabel(note.title)
        QVBoxLayout(self).addWidget(self.label)

    def open_tile(self):
        if self.type == 0:
            note_window = NoteWindow(self.note)
            note_window.resize(300,400)
    
            #note_window.title_changed.connect(self.change_note)
    
            return note_window

        pass

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
            self.create_note_tile(note, 0)

        #move add note from the tile and make the tile call a method that will process the add request
        self.create_note_tile(generic_add_note_tile, 1) 

    def create_note_tile(self, note, type):
        note_tile = NoteTile(note, type)
        note_tile.setObjectName("note")
        note_tile.clicked.connect(lambda: self.open_note(note_tile))
        note_tile.setStyleSheet(note_style)
        note_tile.setFixedSize(200,300)
        self.core_layout.addWidget(note_tile)
        self.tiles[note.uuid] = note_tile

        #self.label.connect

    def add_note(self, note):
        self.delete_note(self.tiles.popitem()[1])
        self.create_note_tile(note, lambda: self.open_note(note))
        self.create_note_tile(generic_add_note_tile, lambda: self.add_note(generic_placeholder_tile))

    #rework later
    def delete_note(self, tile):
        print(tile)
        tile.deleteLater()

    def open_note(self, note_tile):
        window = note_tile.open_tile()
        self.open_note_windows.append(window)
        window.title_changed.connect(self.change_note)
        window.show()


    def change_note(self, uuid, text):
        print("triggered change note")
        print(uuid, text)
        tile_to_change = self.tiles.get(uuid)
        tile_to_change.label.setText(text)
        return


app = QApplication(sys.argv)
app.setStyleSheet(_style)

window = MainWindow()
window.show()

app.exec()

