import Tkinter
master=Tkinter.Tk()
class clientApplication(Tkinter.Tk):
	"""docstring for clientApplication"""
	def __init__(self, parent):
		Tkinter.Tk.__init__(self, parent)
		self.parent=parent
		self.initialize()

	def initialize(self):
		self.grid()
		#self.entry = Tkinter.Entry(self)
		titleName = Tkinter.Label(self, anchor="center", bg="black")
		titleName.grid(column=0, row=0, columnspan=3, sticky='EW')

		username = Tkinter.Label(self, anchor="center", bg="blue")
		username.grid(column=0, row=1, sticky='EW')
		
		Name = Tkinter.Label(self, anchor="center", bg="white")
		Name.grid(column=0, row=2, sticky='EW')

		age = Tkinter.Label(self, anchor="center", bg="yellow")
		age.grid(column=0, row=3, sticky='EW')

		email = Tkinter.Label(self, anchor="center", bg="green")
		email.grid(column=0, row=4, sticky='EW')

if __name__ == '__main__':
	app=clientApplication(None)
	app.title("GUI test")
	app.mainloop()