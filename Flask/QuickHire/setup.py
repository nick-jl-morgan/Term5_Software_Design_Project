from setuptools import setup

setup(
    name='QuickHire',
    packages=['QuickHire'],
    include_package_data=True,
    install_requires=[
        'flask','flask_sqlalchemy','sqlalchemy','flask-restful','flask-jwt-extended','passlib'
    ],
)
