from models import *
from interface import Interface, implements
from objdict import ObjDict
import json , sys , random 
import os


class User:
	@staticmethod
	def createUser(username,password):
		#Check if user already exists
		if UserModel.find_by_username(username):
			raise ValueError('This User Already Exists')
			
		hashedPassword = UserModel.generate_hash(password)
		new_user = UserModel(
            username = username,
            password = hashedPassword
        )
		try:
			new_user.save_to_db()
		except:
			raise ValueError('DataBase Error')
		finally:
			return

	@staticmethod
	def loginUser(username, password):
		
		 current_user = UserModel.find_by_username(username)
		 
		 #Check if user exist 
		 if not UserModel.find_by_username(username):  
			 raise ValueError('User does not exist')
		 
		 if not UserModel.verify_hash( password ,current_user.password):
		 	 raise ValueError('Incorrect password')
		
		 return

	@staticmethod
	def getIdFromUsername(username):
		model = UserModel()
		return model.getID(username)







class Posting:
	def createAccessKey(self):
		done = False
		key = random.randint(100000,999999)
		while (done==False) :
			if self.__model.keyExists( key ) :
				key = random.randint(100000,999999)
			else : 
				done = True

		return key

	def getUserPostings(self, username):

		Userid = User.getIdFromUsername(username)


		postingsModels = self.__model.getUserPostings(Userid)
		info = {}
		info['postings'] = []
		for posting in postingsModels:
			post = {}
			post['accessKey'] = posting.access_key
			post['description'] = posting.description
			post['title'] = posting.title
			info['postings'].append(post)
		return info		




	def __init__(self,owner_id=None,jobTitle=None,company=None,description=None,questions=None):
		self.__model = PostingModel()
		if questions == None :
			self.__questions = []
		else : 
			self.__questions = questions
		self.__model.owner_id = owner_id
		self.__model.description = description
		self.__model.title = jobTitle
		self.__model.company_name = company
		self.__model.access_key = self.createAccessKey()
		return	
	'''
	Comit Posting is used to save the given Posting object to the database.
	This then propogates saveing all of its associated classes to the database as well. Such as 
	its questions.
	'''
	def commitPosting(self):
		postID = self.__model.save_to_db() 
		if self.__questions :
			for question in self.__questions:
				try:
					 question.commitQuestion(postID)	
				except Exception , error:
					print >>sys.stderr, str(error)
		return

	def getAccessKey(self):
		key = self.__model.access_key
		if key :
		 	return key
		else :
		 	return 0

	
	def getPostingFromAccessCode(self,code):
		self.__model = self.__model.getPostingFromAccessCode(code)
		#self.__questions[:]=[]
		if self.__model == None :
			print >>sys.stderr, "No Posting Found"
			raise ValueError('No Posting Found')
		else:
			self.loadQuestions()	
		pass


	def loadQuestions(self):
		posting_id = self.__model.id
		textQuestionsModel = TextQuestionModel() 
		
		#Get all the text question models associated with the posting ID
		questions = textQuestionsModel.getTextQuestionsFromPostId(posting_id)

		#Convert Models into Classes for questions[] list
		for question in questions:
			if (question.answer_type==1):
				vidQuestion = VideoQuestion()
				vidQuestion.getFromModel(question)
				self.__questions.append(vidQuestion)

			elif(question.is_multiple_choice == 1):
				multipleChoice = multipleChoiceQuestion()
				multipleChoice.getFromModel(question)
				self.__questions.append(multipleChoice) 
			
			elif(question.is_multiple_choice == 0):
				textQuestion = TextQuestion()
				textQuestion.getFromModel(question)

				self.__questions.append(textQuestion)
			else :
				pass

	def getPostIDfromAccessKey(self,key):
		postId = self.__model.getIDfromAccessKey(key)
		return postId



	def getQuestions(self):
		return self.__questions

	def getInfo(self):
		info = {}
		info['postID'] = self.__model.id
		info['ownerID'] = self.__model.owner_id
		info['description']= self.__model.description
		info['title'] = self.__model.title
		info['company'] = self.__model.company_name
		info['accessKey'] = self.__model.access_key
		info['questions']=[]

		for question in self.__questions:
			info['questions'].append(question.getInfo())		
		return info
		


'''
The purpose of the question interfce is that when a posting is given a list of questions, 
it can be promised that all questions will have certain functions.

An example of this is in the commitPosting method. This method is given a list of questions. It then assigns
a posting ID to each question and then commits it via the commitQuestion method. By having this method be an attribute of the
interface, the commitPosting method does not need to know what kind of question its working with as it is promised that 
all questions have this method. 
'''
class QuestionInterface(Interface):	
	def commitQuestion(self,post_id):
		pass
	def getInfo(self):
		pass
	def getFromModel(self, textModel):
		pass





class VideoQuestion(implements(QuestionInterface)):
	def __init__(self , question=None , restriction=None ):
		self.__model = TextQuestionModel()
		self.__model.question = question
		self.__model.answer_restriction = restriction
		self.__model.answer_type = 1
	
	def commitQuestion(self, post_id):
		try:
	 	 	self.__model.posting_id = post_id
	 	 	self.__model.save_to_db()
	 	except Exception, e:
	 	 	print >>sys.stderr, "Failed To save Text Question To DataBase"
	 	pass

	def getFromModel(self, textModel):
	 	#Assign given text question Model to class object
	 	self.__model = textModel
	 	pass 


	def getInfo(self):
		info = {}
	 	info['type'] = '0'
	 	info['question'] = self.__model.question
	 	info['id']= self.__model.id
	 	info['length'] = self.__model.answer_restriction
	 	return info		








'''
Text Questions are questions that are text and expect a text response. 
'''
class TextQuestion(implements(QuestionInterface)):
	def __init__(self , question=None):
		self.__model = TextQuestionModel()
	 	self.__model.question = question

	def commitQuestion(self,post_id):
	 	try:
	 	 	self.__model.posting_id = post_id
	 	 	self.__model.save_to_db()
	 	except Exception, e:
	 	 	print >>sys.stderr, "Failed To save Text Question To DataBase"
	 	pass

	def getInfo(self):
	 	info = {}
	 	info['type'] = '1'
	 	info['question'] = self.__model.question
	 	info['id']= self.__model.id
	 	return info

	def getFromModel(self, textModel):
	 	#Assign given text question Model to class object
	 	self.__model = textModel
	 	pass 	 


'''
Multiple Choice Question class implements the Question Interface insuring that it has a commit question
method. This class is also associated with the textQuestion Model, although in the class initializer the 
appropriate model flags are set for this class. While seperating the information that MultipleChoiceQuestion 
and TextQuestion hold doesnt make sense at the database table level, it does at the class level. It is because of this
that we use different classes associated with the same models.   
'''
class multipleChoiceQuestion(implements(QuestionInterface)):
	 
	def __init__(self , question=None, optionslist=None):
	 	if optionslist == None :
	 		self.__options = []
	 	else : 
	 		self.__options = optionslist
	 	self.__model = TextQuestionModel()
	 	self.__model.question = question
	 	self.__model.is_multiple_choice = 1

	def commitQuestion(self,post_id):
	 	try:
	 	 	self.__model.posting_id = post_id
	 		self.__model.save_to_db()
	 	 	for option in self.__options :
	 	 	 	option.commitOption(self.__model.id)
	 	except Exception, error :
	 	 	raise error
	 	pass

	def getFromModel(self, textModel):
	 	#Assign given text question Model to class object
	 	self.__model = textModel
	 	self.__options = multipleChoiceOption.getOptionsFromId(self.__model.id)
	 	pass

	def getInfo(self):
	 	info = {}
	 	info['type']=2
	 	info['id']= self.__model.id
	 	info['question'] = self.__model.question
	 	info['options'] = []
	 	for option in self.__options:
	 	 	info['options'].append(option.getOption())

	 	return info


	 	 
	 	



'''
The multiple choice questions contain an array of multiple choice option instances. These instances can be used to 
save each option to the data base. The multiple choice option class is only accessible through its parent question class.
'''
class multipleChoiceOption():
	__model = None
	 
	def __init__(self , option):
	 	self.__model = MultipleChoiceOptionModel()
	 	self.__model.option = option
	 
	 	 

	def commitOption(self,question_id):
	 	try:
	 	 	self.__model.question_id = question_id
	 	 	self.__model.save_to_db()
	 	except :
	 	 	raise ValueError('DataBase error on saving multipleChoiceOption :' + self.model.option + ' Error : ' + str(error))
	 	 	pass
	 

	def getOption(self):
	 	if(self.__model.option != None):
	 		return self.__model.option
	 	else :
	 		return None


	@staticmethod
	def getOptionsFromId(id):
	 	optionslist = []

	 	model = MultipleChoiceOptionModel()
	 	options = model.getOptionsFromId(id)
	 	
	 	for option in options:
	 	 	optionslist.append(multipleChoiceOption(option.option))
	 	return optionslist






class Application:

	def __init__( self , owner_id=None , post_id=None , answersArray=[]):
		self.__model = ApplicationModel()
		self.__model.posting_id = post_id
		self.__answers = answersArray
		self.__model.owner_id = owner_id
		

	def commitApplication(self):
		appID = self.__model.save_to_db() 
		if self.__answers :
			for answer in self.__answers:
				try:
					 answer.commitAnswer(appID)	
				except Exception , error:
					print >>sys.stderr, str(error)
		return appID

	def getApplicantsFromAccessKey(self,key):
		post = Posting()
		postID = post.getPostIDfromAccessKey(key)
		applicants = self.__model.getApplicantsFromPostID(postID)
		print >> sys.stderr , postID
		print >> sys.stderr , applicants
		info = {}
		info['applicants'] = []
		for applicant in applicants :
			app = {}
			#name = UserSettingsModel().getNameFromID(applicant['owner_id'])
			app['applicationID'] = applicant.id
			app['name'] = 'loam'
			app['post_id'] = applicant.posting_id
			app['owner_id'] = applicant.owner_id
			info['applicants'].append(app)

		return info
	






class textAnswer:
	
	def __init__(self, application_ID=None,question_id=None, answer=None ):
		self.__model = TextAnswerModel()
		self.__model.answer = answer
		self.__model.application_id = application_ID 
		print >> sys.stderr , question_id
		self.__model.question_id = question_id

	def commitAnswer(self,appID):
		self.__model.application_id = appID
		self.__model.save_to_db()






class videoAnswer:
	
	uploadsFolder = "/home/liam/Developement/Flask/JrDesign/Term5_Software_Design_Project/Flask/QuickHire/QuickHire/static/"

	def __init__(self , binaryFile=None , questionID=None , applicationID=None , userID=None ):
		self.__model = VideoAnswerModel()
		self.__model.application_id =  applicationID
		self.__model.question_id = questionID
		newVideoName = self.uploadsFolder + str(applicationID) + "-" + str(questionID) + ".mp4" 
		oldVideoName = self.uploadsFolder + binaryFile
		
		print >> sys.stderr ,"Old Video : " +oldVideoName
		print >> sys.stderr ,"New Video : " +newVideoName

		os.rename(oldVideoName, newVideoName)

		self.__model.location = str(applicationID) + "-" + str(questionID) + ".mp4" 
		print >> sys.stderr ,"Old Video : " +oldVideoName
		print >> sys.stderr ,"New Video : " +newVideoName
	
	def commitAnswer(self):
		self.__model.save_to_db()
		

'''
A common function is taking submitted JSON and converting it to python objects that can then 
be worked on.

JSON parser class contains static methods that take JSON dicts and return either an object or list of 
objects. These objects/lists then get passed to other functions via the controller.

'''
class JsonParser:
	
	@staticmethod
	def parseQuestions(questions):

	 	objectList = []
		for question in questions:
			if question['type']==0:
				objectList.append(JsonParser.parseVideoQuestion(question))
			elif question['type']==1 :
				objectList.append(JsonParser.parseTextQuestion(question))
			elif question['type']==2:	
				objectList.append(JsonParser.parseMultipleChoiceQuestion(question))
		return objectList

	@staticmethod
	def parseVideoQuestion(videoQuestion):
		vidquestion = VideoQuestion( videoQuestion['question'], videoQuestion['length'])
		return vidquestion

	@staticmethod
	def parseTextQuestion(questionJson):
		question = TextQuestion(questionJson['question'])
		return question

	@staticmethod
	def parseMultipleChoiceQuestion(multipleChoicequestionJson):
		
		optionsdict = multipleChoicequestionJson['options']
		optionslist = []
		for option in optionsdict: 
			optionslist.append(multipleChoiceOption(option))
		question = multipleChoiceQuestion(multipleChoicequestionJson['question'], optionslist)
		return question

	@staticmethod
	def postingToJSON(posting):
		dictinfo = {}
		jsonInfo = posting.getInfo()
		return jsonInfo


	@staticmethod
	def parseAnswers(AnswersJson = None ):
	 	objectList = []
	 	if AnswersJson == None:
	 		print >> sys.stderr , "No answersArray provided"
	 
		for answer in AnswersJson:
			if answer['type']==0 :
				pass #handled in second request
			elif answer['type']=="1":	
				objectList.append(JsonParser.parseTextAnswer(answer))
			elif answer['type']=="2":
				objectList.append(JsonParser.parseMultipleChoiceAnswer(answer))
		return objectList

	@staticmethod
	def parseTextAnswer(answer):
		print >> sys.stderr , "Video Answer Parser Arguments : "
		print >> sys.stderr , answer

		return textAnswer(None,answer['questionID'],answer['answer'])
		