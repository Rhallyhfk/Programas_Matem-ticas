import tkinter as tk           # Tkinter nos ayudara con la interfaz
from tkinter import messagebox # La caja de mensaje para algunas advertencias
import random as rd            # Generacion de valores "Aleatorios"

FONDO      = "#1e1e1e"  
TEXTO      = "#ffffff"    
CAJA_TEXTO = "#333333"  
BOTON_BG   = "#007acc"   

class Logica:
    def __init__(self, root):
        #Creamos la pantalla principal
        self.root = root
        self.root.title("Evaluador de Lógica (Modo Oscuro)")
        self.root.geometry("500x500")
        self.root.configure(bg=FONDO)

        #Proposiciones del problema
        lbl_desc = tk.Label(root, 
                            text   = "Proposiciones:\n p(x): x <= 7\n q(x): x + 3 es impar\n r(x): x < 3", 
                            font   = ("Arial", 14), 
                            bg     ="#2d2d2d",
                            fg     =TEXTO,
                            justify= "left", padx = 20, pady = 20)
        lbl_desc.pack(fill="x")

        #Problema 
        lbl_formula = tk.Label(root, 
                               text = "Determine: [p(a) v q(b)] -> r(c)", 
                               font = ("Arial", 18, "bold"), 
                               bg   = FONDO,     
                               fg   = "#3a7cb3") 
        lbl_formula.pack(pady=10)

        '''Tambien podemos meter valores manualmente'''
        frame_inputs = tk.Frame(root, bg=FONDO)
        frame_inputs.pack(pady=10)

        #Si el usuario escribe en la pantalla la variable se actualiza sola.
        self.var_a = tk.IntVar(value=0)
        self.var_b = tk.IntVar(value=0)
        self.var_c = tk.IntVar(value=0)

        self.create_input(frame_inputs, "Valor a:", self.var_a)
        self.create_input(frame_inputs, "Valor b:", self.var_b)
        self.create_input(frame_inputs, "Valor c:", self.var_c)

        # Boton  de Aleatorio
        btn_random = tk.Button(root, text="Generar Datos Aleatorios", command=self.generar_aleatorios, 
                               bg=BOTON_BG, fg=TEXTO, cursor="hand2", 
                               activebackground="#005f99", activeforeground=TEXTO)
        btn_random.pack(pady=5)

        tk.Frame(root, height=2, bd=1, relief="sunken", bg="#555555").pack(padx=20, pady=15)

        # Botones de Respuestas
        lbl_pregunta = tk.Label(root, text="¿Cuál es el valor de verdad?", 
                                font=("Arial", 12), bg=FONDO, fg=TEXTO)
        lbl_pregunta.pack()

        frame_buttons = tk.Frame(root, bg=FONDO)
        frame_buttons.pack(pady=10)

        btn_true = tk.Button(frame_buttons, text="VERDADERO", bg="#2e7d32", fg="white", 
                             font=("Arial", 10, "bold"), width=12,
                             activebackground="#1b5e20", activeforeground="white",
                             command=lambda: self.verificar(True))
        btn_true.pack(side="left", padx=10)

        btn_false = tk.Button(frame_buttons, text="FALSO", bg="#c62828", fg="white", 
                              font=("Arial", 10, "bold"), width=12,
                              activebackground="#b71c1c", activeforeground="white",
                              command=lambda: self.verificar(False))
        btn_false.pack(side="left", padx=10)

        self.lbl_resultado = tk.Label(root, text="", font=("Arial", 12, "bold"), bg=FONDO)
        self.lbl_resultado.pack(pady=10)

        self.generar_aleatorios()

    def create_input(self, parent, text, variable):
        frame = tk.Frame(parent, bg=FONDO)
        frame.pack(side="left", padx=10)
        
        # Etiqueta (a:, b:, c:)
        tk.Label(frame, text=text, bg=FONDO, fg=TEXTO).pack()
        
        # Caja de texto (Entry)
        tk.Entry(frame, textvariable=variable, width=5, justify="center", font=("Arial", 11),
                 bg=CAJA_TEXTO, fg=TEXTO, insertbackground="white").pack()

    def generar_aleatorios(self):
        self.var_a.set(rd.randint(1, 10))
        self.var_b.set(rd.randint(1, 10))
        self.var_c.set(rd.randint(1, 10))
        self.lbl_resultado.config(text="")

    def verificar(self, respuesta_usuario):
        try:
            a = self.var_a.get()
            b = self.var_b.get()
            c = self.var_c.get()

            p = a <= 7
            q = (b + 3) % 2 == 1
            r = c < 3

            # Lógica: (p v q) -> r  es equivalente a  not(p or q) or r
            valor_verdad_real = (not (p or q)) or r

            if valor_verdad_real == respuesta_usuario:
                self.lbl_resultado.config(text="Correcto", fg="#07FF0F") # Verde claro
            else:
                self.lbl_resultado.config(text="Incorrecto (Repasa las tablas de verdad)", fg="#EF0000") # Rojo claro
                
        except tk.TclError:
            messagebox.showerror("Error", "Por favor ingresa solo números enteros.")

if __name__ == "__main__":
    ventana = tk.Tk()
    app = Logica(ventana)
    ventana.mainloop()