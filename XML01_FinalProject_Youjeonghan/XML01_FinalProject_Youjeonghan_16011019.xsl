<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet xmlns:xsl="uri:xsl">
    <xsl:template match="/">
        <xsl:apply-templates />
    </xsl:template>
    <xsl:template match="Insect_Museum">
        <HTML>

            <HEAD>
                <TITLE> 박물관 곤충 관리목록 </TITLE>
            </HEAD>

            <BODY>
                <H1> 박물관 곤충 관리목록 </H1>
                <BR />
                <H3> 한국 곤충 </H3>
                <TABLE border="1">
                    <TR>
                        <TH> 이름 </TH>
                        <TH> 관리번호 </TH>
                        <TH> 이미지 </TH>
                        <TH> 소개 </TH>
                        <TH> 분류 </TH>
                        <TH> 분류설명 </TH>
                        <TH> 평균크기(mm) </TH>
                        <TH> 크기순서 </TH>
                    </TR>
                    <xsl:for-each select="insects/insect" order-by="number(info/size)">
                        <xsl:if test="info/residence_country[.$eq$'한국']">
                            <TR style="text-align:center">
                                <TD>
                                    <xsl:value-of select="name" />
                                </TD>
                                <TD>
                                    <xsl:value-of select="control_number" />
                                </TD>
                                <TD>
                                    <xsl:apply-templates select="ins2:img" />
                                </TD>
                                <TD>
                                    <xsl:value-of select="info/ins2:introduce" />
                                </TD>
                                <TD>
                                    <xsl:value-of select="info/ins2:classification" />
                                </TD>
                                <TD>
                                    <xsl:value-of select="info/ins2:classification_info" />
                                </TD>
                                <TD>
                                    <xsl:value-of select="info/ins2:size" />
                                </TD>
                                <TD>
                                    <xsl:eval>order_korea()</xsl:eval>
                                </TD>
                            </TR>
                        </xsl:if>
                    </xsl:for-each>
                </TABLE>
                <xsl:for-each select="securitys/sec:security">
                    <xsl:if test="sec:section[.$eq$'한국']">
                        <div>
                            담당경비: 
                            <xsl:value-of select="sec:name" />
                        </div>
                        <div>
                            성별:
                            <xsl:value-of select="sec:info/sec:sex" />
                            나이:
                            <xsl:value-of select="sec:info/sec:age" />                            
                        </div>
                        <BR />
                    </xsl:if>
                </xsl:for-each>
                <BR />
                <H3> 일본 곤충 </H3>
                <TABLE border="1">
                    <TR>
                        <TH> 이름 </TH>
                        <TH> 관리번호 </TH>
                        <TH> 이미지 </TH>
                        <TH> 소개 </TH>
                        <TH> 분류 </TH>
                        <TH> 분류설명 </TH>
                        <TH> 평균크기(mm) </TH>
                        <TH> 크기순서 </TH>
                    </TR>
                    <xsl:for-each select="insects/insect" order-by="number(info/size)">
                        <xsl:if test="info/residence_country[.$eq$'일본']">
                            <TR style="text-align:center">
                                <TD>
                                    <xsl:value-of select="name" />
                                </TD>
                                <TD>
                                    <xsl:value-of select="control_number" />
                                </TD>
                                <TD>
                                    <xsl:apply-templates select="ins2:img" />
                                </TD>
                                <TD>
                                    <xsl:value-of select="info/ins2:introduce" />
                                </TD>
                                <TD>
                                    <xsl:value-of select="info/ins2:classification" />
                                </TD>
                                <TD>
                                    <xsl:value-of select="info/ins2:classification_info" />
                                </TD>
                                <TD>
                                    <xsl:value-of select="info/ins2:size" />
                                </TD>
                                <TD>
                                    <xsl:eval>order_japen()</xsl:eval>
                                </TD>
                            </TR>
                        </xsl:if>
                    </xsl:for-each>
                </TABLE>
                <xsl:for-each select="securitys/sec:security">
                    <xsl:if test="sec:section[.$eq$'일본']">
                        <div>
                            담당경비: 
                            <xsl:value-of select="sec:name" />
                        </div>
                        <div>
                            성별:
                            <xsl:value-of select="sec:info/sec:sex" />
                            나이:
                            <xsl:value-of select="sec:info/sec:age" />                            
                        </div>
                        <BR />
                    </xsl:if>
                </xsl:for-each>
                <BR />

                <H3> 중국 곤충 </H3>
                <TABLE border="1">
                    <TR>
                        <TH> 이름 </TH>
                        <TH> 관리번호 </TH>
                        <TH> 이미지 </TH>
                        <TH> 소개 </TH>
                        <TH> 분류 </TH>
                        <TH> 분류설명 </TH>
                        <TH> 평균크기(mm) </TH>
                        <TH> 크기순서 </TH>
                    </TR>
                    <xsl:for-each select="insects/insect" order-by="number(info/size)">
                        <xsl:if test="info/residence_country[.$eq$'중국']">
                            <TR style="text-align:center">
                                <TD>
                                    <xsl:value-of select="name" />
                                </TD>
                                <TD>
                                    <xsl:value-of select="control_number" />
                                </TD>
                                <TD>
                                    <xsl:apply-templates select="ins2:img" />
                                </TD>
                                <TD>
                                    <xsl:value-of select="info/ins2:introduce" />
                                </TD>
                                <TD>
                                    <xsl:value-of select="info/ins2:classification" />
                                </TD>
                                <TD>
                                    <xsl:value-of select="info/ins2:classification_info" />
                                </TD>
                                <TD>
                                    <xsl:value-of select="info/ins2:size" />
                                </TD>
                                <TD>
                                    <xsl:eval>order_china()</xsl:eval>
                                </TD>
                            </TR>
                        </xsl:if>
                    </xsl:for-each>
                </TABLE>
                <xsl:for-each select="securitys/sec:security">
                    <xsl:if test="sec:section[.$eq$'중국']">
                        <div>
                            담당경비: 
                            <xsl:value-of select="sec:name" />
                        </div>
                        <div>
                            성별:
                            <xsl:value-of select="sec:info/sec:sex" />
                            나이:
                            <xsl:value-of select="sec:info/sec:age" />                            
                        </div>
                        <BR />
                    </xsl:if>
                </xsl:for-each>
                <BR />

                <H3> 미국 곤충 </H3>
                <TABLE border="1">
                    <TR>
                        <TH> 이름 </TH>
                        <TH> 관리번호 </TH>
                        <TH> 이미지 </TH>
                        <TH> 소개 </TH>
                        <TH> 분류 </TH>
                        <TH> 분류설명 </TH>
                        <TH> 평균크기(mm) </TH>
                        <TH> 크기순서 </TH>
                    </TR>
                    <xsl:for-each select="insects/insect" order-by="number(info/size)">
                        <xsl:if test="info/residence_country[.$eq$'미국']">
                            <TR style="text-align:center">
                                <TD>
                                    <xsl:value-of select="name" />
                                </TD>
                                <TD>
                                    <xsl:value-of select="control_number" />
                                </TD>
                                <TD>
                                    <xsl:apply-templates select="ins2:img" />
                                </TD>
                                <TD>
                                    <xsl:value-of select="info/ins2:introduce" />
                                </TD>
                                <TD>
                                    <xsl:value-of select="info/ins2:classification" />
                                </TD>
                                <TD>
                                    <xsl:value-of select="info/ins2:classification_info" />
                                </TD>
                                <TD>
                                    <xsl:value-of select="info/ins2:size" />
                                </TD>
                                <TD>
                                    <xsl:eval>order_usa()</xsl:eval>
                                </TD>
                            </TR>
                        </xsl:if>
                    </xsl:for-each>
                </TABLE>
                <xsl:for-each select="securitys/sec:security">
                    <xsl:if test="sec:section[.$eq$'미국']">
                        <div>
                            담당경비: 
                            <xsl:value-of select="sec:name" />
                        </div>
                        <div>
                            성별:
                            <xsl:value-of select="sec:info/sec:sex" />
                            나이:
                            <xsl:value-of select="sec:info/sec:age" />                            
                        </div>
                        <BR />
                    </xsl:if>
                </xsl:for-each>
            </BODY>

        </HTML>
    </xsl:template>
    <xsl:template match="ins2:img">
        <DIV ALIGN="center">
            <IMG>
                <xsl:attribute name="src">
                    <xsl:value-of select="@src" />
                </xsl:attribute>
                <xsl:attribute name="width">
                    <xsl:value-of select="@width" />
                </xsl:attribute>
                <xsl:attribute name="height">
                    <xsl:value-of select="@height" />
                </xsl:attribute>
                <xsl:attribute name="alt">
                    <xsl:value-of select="@alt" />
                </xsl:attribute>
            </IMG>
        </DIV>
    </xsl:template>
    <xsl:script>
        <![CDATA[ var size_korea = 1;
		var size_japen = 1;
		var size_china = 1;
		var size_usa = 1;
		function order_korea() 
		{ 
			return size_korea++;
		} 
		function order_japen() 
		{ 
			return size_japen++;
		} 
		function order_china() 
		{ 
			return size_china++;
		} 
		function order_usa() 
		{ 
			return size_usa++;
		} 
		]]>
    </xsl:script>
</xsl:stylesheet>