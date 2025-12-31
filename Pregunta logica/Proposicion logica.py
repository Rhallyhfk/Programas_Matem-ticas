import tkinter as tk           # Librería para crear interfaz gráfica
import tkinter.messagebox as messagebox  # Para mostrar cuadros de diálogo

from random import randint     # Generacion de valores pseudoaleatorios enteros
from enum import Enum          # Para definir constantes con una sintaxis fácil de organizar y leer


class Color(Enum):
    FONDO      = "#1e1e1e"
    TEXTO      = "#ffffff"
    CAJA_TEXTO = "#333333"
    BOTON_BG   = "#007acc"


class Logica:
    """
    Clase principal para crear la ventana de la aplicación.

    :ventana_raiz: Instancia de tk.Tk que representa la ventana principal.
    """
    def __init__(self, ventana_raiz: tk.Tk) -> None:
        # Creación de la pantalla principal
        self.ventana_raiz = ventana_raiz
        self.ventana_raiz.title("Evaluador de Lógica (Modo Oscuro)")
        self.ventana_raiz.geometry("500x500")
        self.ventana_raiz.configure(bg=Color.FONDO.value)

        # Proposiciones del problema
        lbl_desc = tk.Label(
            master  = ventana_raiz,
            text    = "Proposiciones:\n p(x): x <= 7\n q(x): x + 3 es impar\n r(x): x < 3",
            font    = ("Arial", 14),
            bg      ="#2d2d2d",
            fg      = Color.TEXTO.value,
            justify = "left",
            padx    = 20,
            pady    = 20
        )
        lbl_desc.pack(fill="x")

        # tecto del problema
        lbl_formula = tk.Label(
            master = ventana_raiz,
            text   = "Determine: [p(a) v q(b)] -> r(c)",
            font   = ("Arial", 18, "bold"),
            bg     = Color.FONDO.value,
            fg     = "#3a7cb3"
        )
        lbl_formula.pack(pady=10)

        frame_entradas = tk.Frame(ventana_raiz, bg=Color.FONDO.value)
        frame_entradas.pack(pady=10)

        # Variables en donde se guarda la entrada de usuario.
        # Tienen valor por defecto 0
        self.var_a = tk.IntVar()
        self.var_b = tk.IntVar()
        self.var_c = tk.IntVar()

        # Entradas de usuario
        self.__crear_entrada(frame_entradas, "Valor a:", self.var_a)
        self.__crear_entrada(frame_entradas, "Valor b:", self.var_b)
        self.__crear_entrada(frame_entradas, "Valor c:", self.var_c)

        # Boton datos de aleatorios
        btn_random = tk.Button(
            master              = ventana_raiz,
            text                = "Generar Datos Aleatorios",
            command             = self.__generar_valores_aleatorios,
            bg                  = Color.BOTON_BG.value,
            fg                  = Color.TEXTO.value,
            cursor              = "hand2",
            activebackground    = "#005f99",
            activeforeground    = Color.TEXTO.value
        )
        btn_random.pack(pady=5)

        # Separador
        tk.Frame(ventana_raiz, height=2, bd=1, relief="sunken", bg="#555555").pack(padx=20, pady=15)

        # Texto de la pregunta
        lbl_pregunta = tk.Label(
            master = ventana_raiz,
            text   = "¿Cuál es el valor de verdad?",
            font   = ("Arial", 12),
            bg     = Color.FONDO.value,
            fg     = Color.TEXTO.value
        )
        lbl_pregunta.pack()

        # Marco para los botones VERDADERO / FALSO
        frame_botones = tk.Frame(ventana_raiz, bg=Color.FONDO.value)
        frame_botones.pack(pady=10)

        btn_true = tk.Button(
            master           = frame_botones,
            text             = "VERDADERO",
            bg               = "#2e7d32",
            fg               = "white",
            font             = ("Arial", 10, "bold"),
            width            = 12,
            activebackground = "#1b5e20",
            activeforeground = "white",
            command          = lambda: self.__verificar(True) # Llama a verificar con True
        )
        btn_true.pack(side="left", padx=10)

        btn_false = tk.Button(
            master= frame_botones,
            text="FALSO",
            bg="#c62828",
            fg="white",
            font=("Arial", 10, "bold"),
            width=12,
            activebackground="#b71c1c", activeforeground="white",
            command=lambda: self.__verificar(False)
        )
        btn_false.pack(side="left", padx=10)

        # Etiqueta para mostrar el resultado
        self.lbl_resultado = tk.Label(ventana_raiz, text="", font=("Arial", 12, "bold"), bg=Color.FONDO.value)
        self.lbl_resultado.pack(pady=10)

        self.__generar_valores_aleatorios()


    def __crear_entrada(self, padre: tk.Frame, texto_etiqueta: str, variable: tk.Variable) -> None:
        """
        Abstracción para crear una entrada de usuario con su respectiva etiqueta etiqueta.

        :param padre: Frame padre donde se añadirá la entrada
        :param texto_etiqueta: Texto de la etiqueta sobre cada entrada
        :param variable: referencia a la variable de Tkinter que almacenará el valor ingresado
        """
        frame = tk.Frame(padre, bg=Color.FONDO.value)
        frame.pack(side="left", padx=10)

        # Etiqueta (a:, b:, c:)
        tk.Label(frame, text=texto_etiqueta, bg=Color.FONDO.value, fg=Color.TEXTO.value).pack()

        # Caja de texto (Entry)
        tk.Entry(
            master           = frame,
            textvariable     = variable,
            width            = 5,
            justify          = "center",
            font             = ("Arial", 11),
            bg               = Color.CAJA_TEXTO.value,
            fg               = Color.TEXTO.value,
            validate         = "key",
            validatecommand  = (frame.register(lambda P: P.isdigit()), "%S"),   # Solo permite dígitos enteros
            insertbackground = "white"
        ).pack()


    def __generar_valores_aleatorios(self) -> None:
        """Guarda valores aleatorios entre 1 y 10 en las variables a, b y c. y los muestra automáticamente en las entradas de usuario."""
        self.var_a.set(randint(1, 10))
        self.var_b.set(randint(1, 10))
        self.var_c.set(randint(1, 10))
        self.lbl_resultado.config(text="")  # Limpia el resultado previo


    def __verificar(self, respuesta_usuario: bool) -> None:
        """
        Función llamada al presionar los botones VERDADERO / FALSO.
        Verifica si la respuesta del usuario es correcta.
        Genera texto de resultado en la interfaz.

        :param respuesta_usuario: Boolean que representa la respuesta del usuario (True o False)
        """

        # Retorno anticipado
        try:
            a = self.var_a.get()
            b = self.var_b.get()
            c = self.var_c.get()

        except tk.TclError:
            messagebox.showerror("Error de entrada", "Por favor, ingresa valores válidos para a, b y c.")
            return

        p = a <= 7
        q = (b + 3) % 2 == 1
        r = c < 3

        # Lógica: (p v q) -> r  es equivalente a  not(p or q) or r
        valor_verdad_real = (not (p or q)) or r

        if valor_verdad_real == respuesta_usuario:
            self.lbl_resultado.config(text="Correcto", fg="#07FF0F") # Verde claro
        else:
            self.lbl_resultado.config(text="Incorrecto (Repasa las tablas de verdad)", fg="#EF0000") # Rojo claro


# Punto de entrada de la aplicación
if __name__ == "__main__":
    ventana = tk.Tk()
    app = Logica(ventana)
    ventana.mainloop()