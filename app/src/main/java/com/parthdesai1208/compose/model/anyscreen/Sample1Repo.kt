package com.parthdesai1208.compose.model.anyscreen

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface EmailsRepository {
    fun getAllEmails(): Flow<List<Email>>
    fun getCategoryEmails(category: MailboxType): Flow<List<Email>>
    fun getAllFolders(): List<String>
//    fun getEmailFromId(id: Long): Flow<Email?>
}

class EmailsRepositoryImpl : EmailsRepository {

    override fun getAllEmails(): Flow<List<Email>> = flow {
        emit(LocalEmailsDataProvider.allEmails)
    }

    override fun getCategoryEmails(category: MailboxType): Flow<List<Email>> = flow {
        val categoryEmails = LocalEmailsDataProvider.allEmails.filter { it.mailbox == category }
        emit(categoryEmails)
    }

    override fun getAllFolders(): List<String> {
        return LocalEmailsDataProvider.getAllFolders()
    }

    /*override fun getEmailFromId(id: Long): Flow<Email?> = flow {
        val categoryEmails = LocalEmailsDataProvider.allEmails.firstOrNull { it.id == id }
    }*/
}