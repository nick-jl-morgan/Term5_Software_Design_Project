from QuickHire import db
from sqlalchemy.dialects import mysql
from sqlalchemy import Column, DateTime, String, Integer, ForeignKey, func
from sqlalchemy.orm import relationship, backref
from sqlalchemy.ext.declarative import declarative_base
from passlib.hash import pbkdf2_sha256 as sha256 

class UserModel(db.Model):
    __tablename__ = 'users'

    id = db.Column(db.Integer, primary_key = True)
    username = db.Column(db.String(120), unique = True, nullable = False)
    password = db.Column(db.String(120), nullable = False)
    name = db.Column(db.String(120))
    

    @classmethod
    def generate_hash(cls, password):
        return sha256.hash(password)    
    
    @staticmethod
    def verify_hash(password, hash):
        return sha256.verify(password, hash)

    @classmethod
    def find_by_username(cls, username):
		return cls.query.filter_by(username = username).first()

    def save_to_db(self):
		db.session.add(self)
		db.session.commit()
		idToUse= self.id
		db.session.close()
		return idToUse

    def getID( cls , name ):
    	return cls.query.filter_by( username = name ).first().id

    def getNameFromID(self, userID):
    	return self.query.filter_by(id = userID).first().name


	


class PostingModel(db.Model):
	__tablename__ = 'postings'
	id = db.Column(db.Integer, primary_key = True)
	access_key = db.Column(Integer, nullable=False)
	owner_id = db.Column(Integer, db.ForeignKey(UserModel.id),  nullable=False)
	description = db.Column(mysql.LONGTEXT, nullable=True)
	title = db.Column(mysql.TEXT, nullable=False)
	company_name = db.Column(mysql.VARCHAR(100))

	def save_to_db(self):
		db.session.add(self)
		db.session.commit()
		idToUse= self.id
		db.session.close()
		return idToUse
		
	def getPostingFromAccessCode(self,code):
	    return self.query.filter_by(access_key = code).first()

	def keyExists(self , key):
		return self.query.filter_by(access_key = key).first()

	def getUserPostings(self, userID):
		return self.query.filter_by(owner_id = userID).all()		

	def getIDfromAccessKey(self , key):
		return self.query.filter_by(access_key = key).first().id

	def getPostingFromID(self,postID):
		return self.query.filter_by(id = postID).first()		



class QuestionModel(db.Model):
	__tablename__='questions'
	id = Column(Integer, primary_key = True)
	posting_id = Column(Integer,db.ForeignKey(PostingModel.id),nullable=True)
	description = db.Column(mysql.TEXT)







class TextQuestionModel(db.Model):
	__tablename__='textQuestions'
	id = Column(Integer, primary_key = True)
	posting_id = Column(Integer,db.ForeignKey(PostingModel.id),nullable=True)
	question = db.Column(mysql.TEXT)
	is_multiple_choice = Column(Integer, default=0)
	answer_type = Column(Integer, default=0)
	answer_restriction = db.Column(mysql.BIGINT , default=0)

	def save_to_db(self):
		db.session.add(self)
		db.session.commit()

	
	def getTextQuestionsFromPostId(self,postID):
		db.session.flush()
		results =  self.query.filter_by(posting_id = postID).all()
		return results







class MultipleChoiceOptionModel(db.Model):
	__tablename__='multipleChoiceOptions'
	id = Column(Integer, primary_key = True)
	question_id = Column(Integer,db.ForeignKey(TextQuestionModel.id),nullable=True)
	option = db.Column(mysql.TEXT)

	def save_to_db(self):
		db.session.add(self)
		db.session.commit()

	def getOptionsFromId(self,Qid):
		return self.query.filter_by(question_id=Qid).all()
		






class ApplicationModel(db.Model):
	__tablename__ = 'applications'

	id = Column(Integer,primary_key = True)
	posting_id = Column(Integer,db.ForeignKey(PostingModel.id), nullable=True )
	owner_id = db.Column(Integer, db.ForeignKey(UserModel.id),  nullable=False )

	def save_to_db(self):
		db.session.add(self)
		db.session.commit()
		idToUse= self.id
		db.session.close()
		return idToUse	

	def getApplicantsFromPostID(self , postId):
		return self.query.filter_by(posting_id=postId).all()

	def reserveID(self):
		return self.query.order_by("id desc").first().id

	def getFromID(self,appID):
		return self.query.filter_by(id=appID).first()




class TextAnswerModel(db.Model):
	__tablename__ = 'textAnswers'
	id = Column(Integer,primary_key = True)
	application_id = Column(Integer,db.ForeignKey(ApplicationModel.id), nullable=True )
	question_id = Column(Integer,db.ForeignKey(TextQuestionModel.id), nullable=True )
	answer = db.Column(mysql.TEXT)

	def save_to_db(self):
		db.session.add(self)
		db.session.commit()

	def getFromIDs(self,appID, questionID):
		return self.query.filter_by(application_id=appID).filter_by(question_id=questionID).first().answer




class VideoAnswerModel(db.Model):
	__tablename__ = 'videoAnswers'
	id = Column(Integer,primary_key = True)
	application_id = Column(Integer,db.ForeignKey(ApplicationModel.id), nullable=True )
	location = db.Column(mysql.TEXT)
	question_id = db.Column(Integer, db.ForeignKey(TextQuestionModel.id),  nullable=False )


	def save_to_db(self):
		db.session.add(self)
		db.session.commit()


	def getFromIDs(self,appID, questionID):
		return self.query.filter_by(application_id=appID).filter_by(question_id=questionID).first().location